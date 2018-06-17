package net.fvogel.simpoc.persistence

import net.fvogel.simpoc.model.Facility
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface FacilityRepository : JpaRepository<Facility, Long>