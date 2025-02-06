package ua.edu.chnu.kkn.dag.familytree.sql

import jakarta.persistence.*

@Entity
@Table(name = "connections")
data class ConnectionEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null,

    @ManyToOne
    @JoinColumn(name = "child", referencedColumnName = "user_id")
    val child: IndividualEntity,

    @ManyToOne
    @JoinColumn(name = "father", referencedColumnName = "user_id", nullable = true)
    val father: IndividualEntity?,

    @ManyToOne
    @JoinColumn(name = "mother", referencedColumnName = "user_id", nullable = true)
    val mother: IndividualEntity?

)
