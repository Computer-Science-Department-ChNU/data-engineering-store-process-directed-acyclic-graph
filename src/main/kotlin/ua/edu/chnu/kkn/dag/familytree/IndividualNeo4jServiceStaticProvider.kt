package ua.edu.chnu.kkn.dag.familytree

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ua.edu.chnu.kkn.dag.familytree.neo.IndividualNeo4jService

@Component
class IndividualNeo4jServiceStaticProvider {

    companion object {
        var staticIndividualNeo4jService: IndividualNeo4jService? = null
    }

    @Autowired
    lateinit var individualNeo4JService: IndividualNeo4jService

    @PostConstruct
    fun init() {
        staticIndividualNeo4jService = individualNeo4JService
    }
}