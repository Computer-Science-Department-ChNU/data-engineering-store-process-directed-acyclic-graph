package ua.edu.chnu.kkn.dag.familytree.neo

import org.folg.gedcom.model.GedcomTag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ua.edu.chnu.kkn.dag.familytree.common.*
import ua.edu.chnu.kkn.dag.familytree.common.Sex

@Service
class IndividualNeo4jService {

    @Autowired
    lateinit var individualNeo4jRepository: IndividualNeo4jRepository

    @Transactional
    fun saveAll(gedComTags: List<GedcomTag>) {
        saveIndividuals(gedComTags)
    }

    private fun saveIndividuals(gedComTags: List<GedcomTag>) {
        val individuals = gedComTags
            .filter { it.isIndividual() }
            .map(this::toIndividual)
            .sortedBy { it.id }
        individualNeo4jRepository.saveAll(individuals)
        saveIndividualRelationships(gedComTags, individuals)
    }

    private fun saveIndividualRelationships(
        gedComTags: List<GedcomTag>,
        individuals: List<IndividualNeo4jNode>
    ) {
        val individualsWithRelations = mutableListOf<IndividualNeo4jNode>()
        gedComTags.asSequence()
            .filter { it.isFamily() }
            .forEach { tag ->
                val father = tag.children
                    .filter { it.isHusband() }
                    .firstNotNullOfOrNull { husband ->
                        individuals.find { it.id == husband.ref }
                    }
                val mother: IndividualNeo4jNode? = tag.children
                    .filter { it.isWife() }
                    .firstNotNullOfOrNull { wife ->
                        individuals.find { it.id == wife.ref }
                    }
                tag.children
                    .filter { it.isChild() }
                    .forEach { childTag ->
                        val individual = individuals.find { it.id == childTag.ref }
                        val individualCopy = individual?.copy()
                        individualCopy?.father = father
                        individualCopy?.mother = mother
                        individualCopy?.let { individualsWithRelations.add(it) }
                    }
            }
        individualNeo4jRepository.saveAll(individualsWithRelations)
    }

    private fun toIndividual(tag: GedcomTag) =
        IndividualNeo4jNode(
            id = tag.id,
            name = tag.children[0].value,
            sex = tag.children.find { it.isSex() }?.getSex() ?: Sex.UNDEFINED,
            birthDate = tag.children
                .find { it.isBirthday() }
                ?.children?.find { it.isDate() }
                ?.let { parseDate(it.value) },
            deathDate = tag.children
                .find { it.isDeath() }
                ?.children?.find { it.isDate() }
                ?.let { parseDate(it.value) },
        )

}
