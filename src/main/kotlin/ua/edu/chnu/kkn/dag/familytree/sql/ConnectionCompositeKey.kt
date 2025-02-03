package ua.edu.chnu.kkn.dag.familytree.sql

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ConnectionCompositeKey(
    @Column(name = "child")
    val childId: String,

    @Column(name = "father")
    val fatherId: String,

    @Column(name = "mother")
    val motherId: String
) : Serializable
