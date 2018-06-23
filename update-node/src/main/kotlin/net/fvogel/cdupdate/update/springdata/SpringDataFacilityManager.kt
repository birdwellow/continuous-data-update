package net.fvogel.cdupdate.update.springdata

import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import net.fvogel.cdupdate.model.Facility
import net.fvogel.cdupdate.update.common.FacilityManager
import net.fvogel.cdupdate.update.common.FacilityRepository
import org.hibernate.SessionFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.sql.Date
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.EntityManagerFactory

@Component
@Profile("springdata")
class SpringDataFacilityManager (val entityManagerFactory: EntityManagerFactory,
                                val facilityRepository: FacilityRepository) : FacilityManager {

    internal var sessionFactory: SessionFactory = entityManagerFactory.unwrap(SessionFactory::class.java)

    override fun getFacilities(): List<Facility> {
        return facilityRepository.findAll()
    }

    override fun update() {
        val before = System.currentTimeMillis()
        val facilities = facilityRepository.findAll() as List<Facility>
        for (facility in facilities) {
            update(facility)
        }

        //        updateComplete(facilities);
        updateOneByOneStateless(facilities)

        val diff = System.currentTimeMillis() - before
        log.printf("Updated %d facilities, took %d ms", facilities.size, diff)
        println()
    }

    private fun updateComplete(facilities: List<Facility>) {
        facilityRepository.saveAll(facilities)
    }

    private fun updateOneByOneStateless(facilities: List<Facility>) {
        val session = sessionFactory.openStatelessSession()
        val tx = session.beginTransaction()

        for (facility in facilities) {
            session.update(facility)
        }

        tx.commit()
        session.close()
    }

    private fun update(facility: Facility) {
        val now = System.nanoTime()
        val diff = now - facility.lastUpdate.time
//        facility.lastUpdate = Date.valueOf("")
        facility.lastUpdate = Timestamp.from(Instant.now())

        val newGas = facility.gasFactories * diff / 1000 * RATE_PER_SECOND_GAS
        val newLiquids = facility.liquidsFactories * diff / 1000 * RATE_PER_SECOND_LIQUIDS
        val newSolids = facility.solidsFactories * diff / 1000 * RATE_PER_SECOND_SOLIDS

        facility.gas = (facility.gas + newGas)
        facility.liquids = (facility.liquids + newLiquids)
        facility.solids = (facility.solids + newSolids)
    }

    companion object {
        private val RATE_PER_SECOND_GAS = 1.3f
        private val RATE_PER_SECOND_LIQUIDS = 0.9f
        private val RATE_PER_SECOND_SOLIDS = 0.7f
    }

}