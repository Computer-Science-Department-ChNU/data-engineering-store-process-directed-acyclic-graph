package ua.edu.chnu.kkn.dag.familytree

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class IndividualNeo4jServiceStaticProvider {

    companion object {
        var staticIndividualServiceNeo4j: IndividualServiceNeo4j? = null
    }

    @Autowired
    lateinit var individualServiceNeo4j: IndividualServiceNeo4j

    @PostConstruct
    fun init() {
        staticIndividualServiceNeo4j = individualServiceNeo4j
    }
}