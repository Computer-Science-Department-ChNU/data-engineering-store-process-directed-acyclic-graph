package ua.edu.chnu.kkn.dag.familytree

import jakarta.transaction.Transactional
import org.folg.gedcom.model.GedcomTag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.edu.chnu.kkn.dag.familytree.common.*
import ua.edu.chnu.kkn.dag.familytree.sql.IndividualEntity
import ua.edu.chnu.kkn.dag.familytree.sql.IndividualSqlRepository

@Service
class IndividualServiceSql {

    @Autowired
    lateinit var individualSqlRepository: IndividualSqlRepository

    @Transactional
    fun saveAll(gedcomTags: List<GedcomTag>) {
        saveIndividuals(gedcomTags)
    }

    fun findById(id: String): IndividualEntity? {
        return individualSqlRepository.findById(id).orElse(null)
    }

    private fun saveIndividuals(gedcomTags: List<GedcomTag>) {
        val individuals = gedcomTags
            .filter { it.isIndividual() }
            .map(this::toIndividual)
            .sortedBy { it.user_id }
        individualSqlRepository.saveAll(individuals)
    }

    private fun toIndividual(tag: GedcomTag) =
        IndividualEntity(
            user_id = tag.id,
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