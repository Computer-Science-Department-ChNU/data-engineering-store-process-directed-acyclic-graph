package ua.edu.chnu.kkn.dag.familytree

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class IndividualSqlServiceStaticProvider {

    companion object {
        var staticIndividualSqlService: IndividualServiceSql? = null
    }

    @Autowired
    lateinit var individualServiceSql: IndividualServiceSql

    @PostConstruct
    fun init() {
        staticIndividualSqlService = individualServiceSql
    }
}