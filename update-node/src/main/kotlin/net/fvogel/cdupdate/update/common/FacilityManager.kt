package net.fvogel.cdupdate.update.common

import net.fvogel.cdupdate.model.Facility

interface FacilityManager {

    fun getFacilities(): List<Facility>

    fun update()

}