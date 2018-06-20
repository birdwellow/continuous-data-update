package net.fvogel.cdupdate.model


import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Facility(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,

        var lastUpdate: Long = System.currentTimeMillis(),

        var gas: Float = 0.toFloat(),
        var gasFactories: Int = 0,
        var liquids: Float = 0.toFloat(),
        var liquidsFactories: Int = 0,
        var solids: Float = 0.toFloat(),
        var solidsFactories: Int = 0,

        var powerPlants: Int = 0

)