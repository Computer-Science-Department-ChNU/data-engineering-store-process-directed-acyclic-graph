package ua.edu.chnu.kkn.dag.familytree

import org.folg.gedcom.model.GedcomTag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ua.edu.chnu.kkn.dag.familytree.common.*
import ua.edu.chnu.kkn.dag.familytree.neo.IndividualNeo4jNode
import ua.edu.chnu.kkn.dag.familytree.neo.IndividualNeo4jRepository
import ua.edu.chnu.kkn.dag.familytree.neo.Sex
import java.time.LocalDate

@Service
class IndividualService {

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
        val person = IndividualNeo4jNode(
            id = 9999,
            realId = "TESTER",
            name = "Debuger",
            sex = Sex.MALE,
            birthDate = LocalDate.now(),
            deathDate = LocalDate.now(),
            father = individuals[0],
            mother = individuals[1],
        )
        val person2 = IndividualNeo4jNode(
            id = 9998,
            realId = "TESTER",
            name = "Debuger",
            sex = Sex.MALE,
            birthDate = LocalDate.now(),
            deathDate = LocalDate.now(),
            father = person,
            mother = individuals[4],
        )
        individualNeo4jRepository.save(person)
        individualNeo4jRepository.save(person2)
        individualNeo4jRepository.delete(person)// Deleting deletes all node's connections including in-out.
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
                        individuals.find { it.realId == husband.ref }
                    }
                val mother: IndividualNeo4jNode? = tag.children
                    .filter { it.isWife() }
                    .firstNotNullOfOrNull { wife ->
                        individuals.find { it.realId == wife.ref }
                    }
                tag.children
                    .filter { it.isChild() }
                    .forEach { childTag ->
                        val individual = individuals.find { it.realId == childTag.ref }
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
            id = tag.id.replace("I", "").toInt(),
            realId = tag.id,
            name = tag.children[0].value,
            sex = getSex(tag.children.find { it.isSex() }),
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
