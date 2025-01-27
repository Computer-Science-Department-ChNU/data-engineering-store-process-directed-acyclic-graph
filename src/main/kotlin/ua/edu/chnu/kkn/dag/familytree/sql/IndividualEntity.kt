package ua.edu.chnu.kkn.dag.familytree.sql

import jakarta.persistence.*
import ua.edu.chnu.kkn.dag.familytree.neo.Sex
import java.time.LocalDate

@Entity
data class IndividualEntity(
    @Id
    val id: String,
    val name: String,
    @Column(columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    val sex: Sex,
    @Column(name = "birth_date")
    val birthDate: LocalDate?,
    @Column(name = "death_date")
    val deathDate: LocalDate?,
)
