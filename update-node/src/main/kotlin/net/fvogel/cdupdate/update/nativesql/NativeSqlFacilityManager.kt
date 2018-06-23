package net.fvogel.cdupdate.update.nativesql

import antlr.build.ANTLR.root
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import net.fvogel.cdupdate.model.Facility
import net.fvogel.cdupdate.update.common.FacilityManager
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.Instant
import java.util.Calendar.SECOND
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.criteria.Expression
import javax.transaction.Transactional


@Component
@Profile("nativesql")
class NativeSqlFacilityManager (val entityManager: EntityManager) : FacilityManager {

    override fun getFacilities(): List<Facility> {
        var query = entityManager.createNativeQuery("SELECT * FROM facility", Facility::class.java);
        return query.resultList.toList() as List<Facility>;
    }

    @Transactional
    override fun update() {
        val before = System.currentTimeMillis()

        var nativeUpdateQuery: String =
                "UPDATE facility SET last_update=current_timestamp, gas=gas+(gas_factories* extract(epoch from (current_timestamp - last_update)) *1.3)"

        var updates: Int = entityManager.createNativeQuery(nativeUpdateQuery).executeUpdate();

        val diff = System.currentTimeMillis() - before
        log.printf("Updated %d facilities, took %d ms", updates, diff)
        println()
    }

    companion object {
        private val RATE_PER_SECOND_GAS = 1.3f
        private val RATE_PER_SECOND_LIQUIDS = 0.9f
        private val RATE_PER_SECOND_SOLIDS = 0.7f
    }

}