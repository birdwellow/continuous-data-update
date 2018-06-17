package net.fvogel.cdupdate.persistence

import net.fvogel.cdupdate.model.Facility
import org.springframework.data.jpa.repository.JpaRepository

interface FacilityRepository : JpaRepository<Facility, Long>