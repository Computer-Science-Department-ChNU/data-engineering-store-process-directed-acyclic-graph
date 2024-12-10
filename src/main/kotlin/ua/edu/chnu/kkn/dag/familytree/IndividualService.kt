package ua.edu.chnu.kkn.dag.familytree

import org.folg.gedcom.model.GedcomTag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IndividualService {

    @Autowired
    lateinit var individualRepository: IndividualRepository

    @Transactional
    fun saveAll(gedComTags: List<GedcomTag>) {
        saveIndividuals(gedComTags)
    }

    private fun saveIndividuals(gedComTags: List<GedcomTag>) {
        println("------------Begin saveAll-------------")
        val individuals = gedComTags
            .filter { it.isIndividual() }
            .map(this::toIndividual)
            .sortedBy { it.id }
        individualRepository.saveAll(individuals)

        println("End saveAll")

        saveIndividualRelationships(gedComTags, individuals)

        println("End saveAll with connection")


    }

    private fun saveIndividualRelationships(gedComTags: List<GedcomTag>, individuals: List<Individual> ){
        val individualsWithRelations = mutableListOf<Individual>()
        for (tag in gedComTags) {
            if (tag.tag == "FAM") {
                val tempFather = individuals.find { it.realId == tag.children.getOrNull(0)?.ref && tag.children[0].tag == "HUSB" }
                var tempMother : Individual? = null
                if (tag.children[0].tag == "WIFE"){
                    tempMother = individuals.find { it.realId == tag.children[0].ref }
                }
                else if (tag.children.size > 1 && tag.children[1]?.tag == "WIFE") {
                    tempMother = individuals.find { it.realId == tag.children[1].ref }
                }
                tag.children.filter { it.tag == "CHIL" }.forEach {childTag ->
                    val individ = individuals.find { it.realId == childTag.ref }
                    val individCopy = individ?.copy()

                    individCopy?.father = tempFather
                    individCopy?.mother = tempMother
                    individCopy?.let { individualsWithRelations.add(it) }
                    println("Save with connections ${individ?.id}" )
                }
            }
        }
        individualRepository.saveAll(individualsWithRelations)
    }



    private fun toIndividual(tag: GedcomTag) = Individual(id = tag.id.replace("I", "").toInt(), realId = tag.id, name = tag.children[0].value)
}
