package ua.edu.chnu.kkn.dag.familytree

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node("Book")
data class Book(
    @Id
    val id: String,
    val name: String
)
