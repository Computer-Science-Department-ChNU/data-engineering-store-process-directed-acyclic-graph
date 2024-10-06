package ua.edu.chnu.kkn.dag.familytree

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("Author")
data class Author constructor(
    @Id
    val id: String,
    var name: String,
    @Relationship(type = "AUTHORED")
    var books: List<Book>
)
