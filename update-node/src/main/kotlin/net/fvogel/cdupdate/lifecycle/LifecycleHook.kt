package net.fvogel.cdupdate.lifecycle

import net.fvogel.cdupdate.persistence.FacilityRepository
import net.fvogel.cdupdate.tasking.SchedulingManager
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