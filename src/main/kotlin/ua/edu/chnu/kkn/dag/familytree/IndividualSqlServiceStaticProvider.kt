package ua.edu.chnu.kkn.dag.familytree

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ua.edu.chnu.kkn.dag.familytree.sql.IndividualSqlService

@Component
class IndividualSqlServiceStaticProvider {

    companion object {
        var staticIndividualSqlService: IndividualSqlService? = null
    }

    @Autowired
    lateinit var individualSqlService: IndividualSqlService

    @PostConstruct
    fun init() {
        staticIndividualSqlService = individualSqlService
    }
}