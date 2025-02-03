package ua.edu.chnu.kkn.dag.familytree.sql

import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table

@Entity
@Table(name = "connections")
data class ConnectionEntity(
    @EmbeddedId
    val id: ConnectionCompositeKey,

    @ManyToOne
    @MapsId("child")
    @JoinColumn(name = "child", referencedColumnName = "id")
    val child: IndividualEntity,

    @ManyToOne
    @MapsId("father")
    @JoinColumn(name = "father", referencedColumnName = "id")
    val father: IndividualEntity,

    @ManyToOne
    @MapsId("mother")
    @JoinColumn(name = "mother", referencedColumnName = "id")
    val mother: IndividualEntity

)
