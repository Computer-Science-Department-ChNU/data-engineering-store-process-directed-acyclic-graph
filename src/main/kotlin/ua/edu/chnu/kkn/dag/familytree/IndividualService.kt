package ua.edu.chnu.kkn.dag.familytree

import org.folg.gedcom.model.GedcomTag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class IndividualService {

    @Autowired
    lateinit var individualRepository: IndividualRepository

    fun saveAll(gedComTags: List<GedcomTag>) {
        individualRepository.saveAll(gedComTagsToIndividual(gedComTags))
    }

    private fun gedComTagsToIndividual(gedComTags: List<GedcomTag>): List<Individual> {
        val individuals = gedComTags
            .asSequence()
            .filter { it.isIndividual() }
            .map(this::toIndividual)
            .toList()
        for (tag in gedComTags) {
            if (tag.tag == "FAM") {
                val tempFather = individuals.find { it.id == tag.children[0].ref && tag.children[0].tag == "HUSB" }
                var tempMother : Individual? = null
                if (tag.children[0].tag == "WIFE"){
                    tempMother = individuals.find { it.id == tag.children[0].ref }
                }
                else if (tag.children.size > 1 && tag.children[1]?.tag == "WIFE") {
                    tempMother = individuals.find { it.id == tag.children[1].ref }
                }
                for (tagFam in  tag.children ) {
                    if (tagFam.tag == "CHIL") {
                        val personParrenToAdd = individuals.find { it.id == tagFam.ref }
                        personParrenToAdd?.father = tempFather
                        personParrenToAdd?.mother = tempMother
                    }
                }
            }
        }
        return individuals
    }

    private fun toIndividual(tag: GedcomTag) = Individual(id = tag.id, name = tag.children[0].value)
}
