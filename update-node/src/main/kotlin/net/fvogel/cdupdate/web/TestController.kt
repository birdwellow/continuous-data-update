package net.fvogel.cdupdate.web

import net.fvogel.cdupdate.model.Facility
import org.hibernate.SessionFactory
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityManagerFactory

@RestController
@RequestMapping("/test")
class TestController(val entityManagerFactory: EntityManagerFactory) {

    internal var sessionFactory: SessionFactory = entityManagerFactory.unwrap(SessionFactory::class.java);

    @PostMapping("/hql")
    fun hqlUpdate(@RequestBody hql: String) {
        var session = sessionFactory.openSession()
        val transaction = session.beginTransaction()
        var updates: Int = session.createQuery(hql).executeUpdate()
        transaction.commit()
        println("Done " + updates + " updates")
    }

    @PutMapping("/hql")
    fun hqlSelect(@RequestBody hql: String) {
        var session = sessionFactory.openSession()
        var facilities: List<Facility> = session.createQuery(hql, Facility::class.java).list()
        println("Found " + facilities.size + " entities")
    }

    @PostMapping("/sql")
    fun sqlUpdate(@RequestBody sql: String) {
        var session = sessionFactory.openSession()
        val transaction = session.beginTransaction()
        var found: MutableList<Any?> = session.createNativeQuery(sql).list()
        transaction.commit()
        println("Found " + found.size + " entities")
    }

}
