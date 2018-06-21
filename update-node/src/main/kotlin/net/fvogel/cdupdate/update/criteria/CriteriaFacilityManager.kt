package net.fvogel.cdupdate.update.criteria

import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import net.fvogel.cdupdate.model.Facility
import net.fvogel.cdupdate.update.common.FacilityManager
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.EntityManager
import javax.transaction.Transactional


@Component
@Profile("criteria")
class CriteriaFacilityManager (val entityManager: EntityManager) : FacilityManager {

    override fun getFacilities(): List<Facility> {
        var query = entityManager.criteriaBuilder.createQuery(Facility::class.java)
        return entityManager.createQuery(query).resultList;
    }

    @Transactional
    override fun update() {
        val before = System.currentTimeMillis()
        var criteriaBuilder = entityManager.criteriaBuilder
        var update = criteriaBuilder.createCriteriaUpdate(Facility::class.java)
        val root = update.from(Facility::class.java)
        update.set("solids", criteriaBuilder.prod());
        update.set("solids", 10.1f);
        update.set("lastUpdate", Timestamp.from(Instant.now()));

        var updates: Int = entityManager.createQuery(update).executeUpdate();

        val diff = System.currentTimeMillis() - before
        log.printf("Updated %d facilities, took %d ms", updates, diff)
        println()
    }

//    private fun update(facility: Facility) {
//        val now = System.currentTimeMillis()
//        val diff = now - facility.lastUpdate
//        facility.lastUpdate = now
//
//        val newGas = facility.gasFactories * diff / 1000 * RATE_PER_SECOND_GAS
//        val newLiquids = facility.liquidsFactories * diff / 1000 * RATE_PER_SECOND_LIQUIDS
//        val newSolids = facility.solidsFactories * diff / 1000 * RATE_PER_SECOND_SOLIDS
//
//        facility.gas = (facility.gas + newGas)
//        facility.liquids = (facility.liquids + newLiquids)
//        facility.solids = (facility.solids + newSolids)
//    }
//
//    companion object {
//        private val RATE_PER_SECOND_GAS = 1.3f
//        private val RATE_PER_SECOND_LIQUIDS = 0.9f
//        private val RATE_PER_SECOND_SOLIDS = 0.7f
//    }

}