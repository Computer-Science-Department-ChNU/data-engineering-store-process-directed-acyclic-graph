package ua.edu.chnu.kkn.dag.familytree

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ConnectionSqlServiceStaticProvider {

    companion object {
        var staticConnectionSqlProvider : ConnectionServiceSql? = null
    }

    @Autowired
    lateinit var connectionServiceSql: ConnectionServiceSql

    @PostConstruct
    fun init() {
        staticConnectionSqlProvider = connectionServiceSql
    }
}