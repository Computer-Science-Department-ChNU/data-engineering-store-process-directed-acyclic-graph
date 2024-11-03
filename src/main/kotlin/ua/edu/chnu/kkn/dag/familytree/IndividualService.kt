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
        //TODO Convert IND tags to Individual objects, fill in children list with Individuals based on FAM tag.
        return emptyList()
    }
}
