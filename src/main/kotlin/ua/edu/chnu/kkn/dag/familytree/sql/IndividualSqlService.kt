package ua.edu.chnu.kkn.dag.familytree.sql

import jakarta.transaction.Transactional
import org.folg.gedcom.model.GedcomTag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.edu.chnu.kkn.dag.familytree.common.*

@Service
class IndividualSqlService {

    @Autowired
    lateinit var individualSqlRepository: IndividualSqlRepository

    @Autowired
    lateinit var connectionSqlRepository : ConnectionSqlRepository

    @Transactional
    fun saveAll(gedcomTags: List<GedcomTag>) {
        saveIndividuals(gedcomTags)
        /*saveRelationships(gedcomTags)*/

    }

    private fun saveIndividuals(gedcomTags: List<GedcomTag>) {
        val individuals = gedcomTags
            .filter { it.isIndividual() }
            .map(this::toIndividual)
            .sortedBy { it.id }
       val result = individualSqlRepository.saveAll(individuals)
        println(result.size)
    }

    private fun saveRelationships(gedComTags: List<GedcomTag>) {

        val connections = mutableListOf<ConnectionEntity>()

        gedComTags.asSequence()
            .filter { it.isFamily() }
            .forEach { tag ->
                val father = tag.children
                    .filter { it.isHusband() }
                    .firstNotNullOfOrNull { husband ->
                        husband?.ref?.let { findById(it) }
                    }
                val mother = tag.children
                    .filter { it.isWife() }
                    .firstNotNullOfOrNull { wife ->
                        wife?.ref?.let { findById(it) }
                    }
                tag.children
                    .filter { it.isChild() }
                    .forEach { childTag ->
                        childTag?.ref?.let {
                            if (findById(it) != null) {
                                connections.add(
                                    ConnectionEntity(
                                        child = findById(it)!!,
                                        father = father,
                                        mother = mother
                                    )
                                )
                            }
                        }
                    }
            }

        connectionSqlRepository.saveAll(connections)
        println("SAVED ALL CONNECTIONS")
    }

    private fun toIndividual(tag: GedcomTag) =
        IndividualEntity(
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

    private fun findById(id: String): IndividualEntity? {
        return individualSqlRepository.findById(id).orElse(null)
    }

}