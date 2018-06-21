package net.fvogel.cdupdate.update.common

import net.fvogel.cdupdate.model.Facility
import org.springframework.data.jpa.repository.JpaRepository

interface FacilityRepository : JpaRepository<Facility, Long>