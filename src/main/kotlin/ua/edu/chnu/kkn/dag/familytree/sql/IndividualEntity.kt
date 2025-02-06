package ua.edu.chnu.kkn.dag.familytree.sql

import jakarta.persistence.*
import ua.edu.chnu.kkn.dag.familytree.common.Sex
import java.time.LocalDate

@Entity
@Table(name = "individuals")
data class IndividualEntity(
    @Id
    val user_id: String,
    val name: String,
    @Column(columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    val sex: Sex,
    @Column(name = "birth_date")
    val birthDate: LocalDate?,
    @Column(name = "death_date")
    val deathDate: LocalDate?,
)
