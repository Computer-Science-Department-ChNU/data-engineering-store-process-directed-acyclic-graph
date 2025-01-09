package ua.edu.chnu.kkn.dag.familytree.neo

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.LocalDate

@Node("Individual")
data class IndividualNeo4jNode(
    @Id
    val id: Int,
    val realId: String,
    val name: String,
    val sex: Sex,
    val birthDate: LocalDate?,
    val deathDate: LocalDate?,
    @Relationship(type = "FATHER", direction = Relationship.Direction.INCOMING)
    var father: IndividualNeo4jNode? = null,
    @Relationship(type = "MOTHER", direction = Relationship.Direction.INCOMING)
    var mother: IndividualNeo4jNode? = null,
)
