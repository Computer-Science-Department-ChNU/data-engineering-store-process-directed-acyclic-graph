package ua.edu.chnu.kkn.dag.familytree

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("Individual")
data class Individual(
    @Id
    val id: String,
    val name: String,
    @Relationship(type = "DESCENDANTS")
    val children: MutableList<Individual>
)