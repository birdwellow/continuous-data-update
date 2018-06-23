package net.fvogel.cdupdate.update.criteria

import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import net.fvogel.cdupdate.model.Facility
import net.fvogel.cdupdate.update.common.FacilityManager
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.transaction.Transactional


// Hibernate Criteria / HQL don't provide any time operations
@Component
@Profile("criteria")
class CriteriaFacilityManager (val entityManager: EntityManager,
                               val entityManagerFactory: EntityManagerFactory) : FacilityManager {

    internal var sessionFactory: SessionFactory = entityManagerFactory.unwrap(SessionFactory::class.java)

    override fun getFacilities(): List<Facility> {
        var query = entityManager.criteriaBuilder.createQuery(Facility::class.java)
        return entityManager.createQuery(query).resultList;
    }

    @Transactional
    override fun update() {
//        updateHql()
        updateObjectOriented()
    }

    fun updateHql() {
        val before = System.currentTimeMillis()
        var session: Session = sessionFactory.openSession()
        var updates: Int = session.createQuery("").executeUpdate();
        val diff = System.currentTimeMillis() - before
        log.printf("Updated %d facilities, took %d ms", updates, diff)
        println()
    }

    fun updateObjectOriented() {
        val before = System.currentTimeMillis()
        var criteriaBuilder = entityManager.criteriaBuilder
        var update = criteriaBuilder.createCriteriaUpdate(Facility::class.java)
        val facility = update.from(Facility::class.java)

        var timediff = criteriaBuilder.function(
                "TIMEDIFF",
                Integer::class.java,
                criteriaBuilder.literal("SECOND"),
                facility.get<Timestamp>("lastUpdate"),
                criteriaBuilder.currentTimestamp()
        )

        update.set(facility.get<Number>("solids"),
                criteriaBuilder.sum(
                        facility.get<Number>("solids"),
                        criteriaBuilder.prod(
                                facility.get<Number>("solidsFactories"),
                                criteriaBuilder.prod(
                                        timediff,
                                        RATE_PER_SECOND_SOLIDS
                                )
                        )
                )
        )

        update.set("lastUpdate", Timestamp.from(Instant.now()));

        var updates: Int = entityManager.createQuery(update).executeUpdate();

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