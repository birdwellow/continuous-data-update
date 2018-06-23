package net.fvogel.cdupdate.model


import java.util.*
import javax.persistence.*

@Entity
data class Facility(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,

//        @Basic
//        var lastUpdate: Timestamp = Timestamp.from(Instant.now()),

        @Basic
        @Temporal(TemporalType.TIMESTAMP)
//        @Column(columnDefinition = "timestamp with time zone")
        var lastUpdate: Date = Date(),

        var gas: Float = 0.toFloat(),
        var gasFactories: Int = 0,
        var liquids: Float = 0.toFloat(),
        var liquidsFactories: Int = 0,
        var solids: Float = 0.toFloat(),
        var solidsFactories: Int = 0,

        var powerPlants: Int = 0

)