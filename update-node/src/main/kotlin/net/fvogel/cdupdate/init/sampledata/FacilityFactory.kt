package net.fvogel.cdupdate.init.sampledata

import net.fvogel.cdupdate.model.Facility
import java.util.*

class FacilityFactory {

    fun create(count: Long): List<Facility> {
        val facilities = ArrayList<Facility>()
        for (i in 0 until count) {
            facilities.add(createRandom())
        }
        println("Created " + facilities.size + " facilities: " + facilities)
        return facilities
    }

    private fun createRandom(): Facility {
        val facility = Facility()

        facility.gas = random(500).toFloat()
        facility.liquids = random(500).toFloat()
        facility.solids = random(500).toFloat()

        facility.gasFactories = random(5).toInt()
        facility.liquidsFactories = random(5).toInt()
        facility.solidsFactories = random(5).toInt()

        return facility
    }

    private fun random(upperBound: Int): Double {
        return (Math.random() * upperBound)
    }

}