package net.fvogel.cdupdate.lifecycle

import net.fvogel.cdupdate.model.Facility
import java.util.*

class FacilityFactory {

    fun create(count: Int): List<Facility> {
        val facilities = ArrayList<Facility>()
        for (i in 0 until count) {
            facilities.add(createRandom())
        }
        println("Created " + facilities.size + " facilities: " + facilities)
        return facilities
    }

    private fun createRandom(): Facility {
        val facility = Facility()

        facility.gas = random500()
        facility.liquids = random500()
        facility.solids = random500()

        facility.gasFactories = random5()
        facility.liquidsFactories = random5()
        facility.solidsFactories = random5()

        return facility
    }

    private fun random500(): Float {
        return (Math.random() * 500).toFloat()
    }

    private fun random5(): Int {
        return (Math.random() * 5).toInt()
    }

}