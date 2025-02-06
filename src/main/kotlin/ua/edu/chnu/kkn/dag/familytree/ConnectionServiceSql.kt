package ua.edu.chnu.kkn.dag.familytree

import jakarta.transaction.Transactional
import org.folg.gedcom.model.GedcomTag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.edu.chnu.kkn.dag.familytree.common.isChild
import ua.edu.chnu.kkn.dag.familytree.common.isFamily
import ua.edu.chnu.kkn.dag.familytree.common.isHusband
import ua.edu.chnu.kkn.dag.familytree.common.isWife
import ua.edu.chnu.kkn.dag.familytree.sql.ConnectionEntity
import ua.edu.chnu.kkn.dag.familytree.sql.ConnectionSqlRepository

@Service
class ConnectionServiceSql {

    @Autowired
    private lateinit var individualServiceSql: IndividualServiceSql

    @Autowired
    lateinit var connectionSqlRepository: ConnectionSqlRepository

    @Transactional
    fun saveAll(gedComTags: List<GedcomTag>) {
        saveRelationships(gedComTags)
    }

    private fun saveRelationships(gedComTags: List<GedcomTag>) {

        val connections = mutableListOf<ConnectionEntity>()

        gedComTags.asSequence()
            .filter { it.isFamily() }
            .forEach { tag ->
                val father = tag.children
                    .filter { it.isHusband() }
                    .firstNotNullOfOrNull { husband ->
                        husband?.ref?.let { individualServiceSql.findById(it) }
                    }
                val mother = tag.children
                    .filter { it.isWife() }
                    .firstNotNullOfOrNull { wife ->
                        wife?.ref?.let { individualServiceSql.findById(it) }
                    }
                tag.children
                    .filter { it.isChild() }
                    .forEach { childTag ->
                        childTag?.ref?.let {
                            if (individualServiceSql.findById(it) != null) {
                                connections.add(
                                    ConnectionEntity(
                                        child = individualServiceSql.findById(it)!!,
                                        father = father,
                                        mother = mother
                                    )
                                )
                            }
                        }
                    }
            }

        connectionSqlRepository.saveAll(connections)
        println("SAVED ALL CONNECTIONS")
    }


}