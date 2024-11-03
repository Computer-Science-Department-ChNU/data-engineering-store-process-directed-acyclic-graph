package ua.edu.chnu.kkn.dag.familytree

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class IndividualServiceStaticProvider {

    companion object {
        var staticIndividualService: IndividualService? = null
    }

    @Autowired
    lateinit var individualService: IndividualService

    @PostConstruct
    fun init() {
        staticIndividualService = individualService
    }
}