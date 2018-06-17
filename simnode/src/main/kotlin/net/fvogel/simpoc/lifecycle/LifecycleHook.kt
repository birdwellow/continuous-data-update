package net.fvogel.simpoc.lifecycle

import net.fvogel.simpoc.logic.FacilityManager
import net.fvogel.simpoc.persistence.FacilityRepository
import net.fvogel.simpoc.tasking.SchedulingManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class LifecycleHook(private val schedulingManager: SchedulingManager) {

    @Autowired
    internal var facilityRepository: FacilityRepository? = null

    init {
        schedulingManager.start()
    }

    @PostConstruct
    fun postContruct() {
        facilityRepository!!.deleteAll()
        val facilityFactory = FacilityFactory()
        val facilities = facilityFactory.create(10000)
        facilityRepository!!.saveAll(facilities)
    }

    @PreDestroy
    fun preDestroy() {
        schedulingManager.stop()
    }

}