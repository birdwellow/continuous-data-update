package net.fvogel.cdupdate.init.sampledata

import net.fvogel.cdupdate.persistence.FacilityRepository
import net.fvogel.cdupdate.tasking.SchedulingManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class RandomSampleDataInitializer(private val schedulingManager: SchedulingManager) {

    internal var guaranteedAmount: Long = System.getProperty("guaranteedAmount", "10000").toLong();
    internal var forceClean: Boolean = System.getProperty("forceClean", "true").toBoolean();

    @Autowired
    internal var facilityRepository: FacilityRepository? = null

    @PostConstruct
    fun postContruct() {
        if (forceClean) {
            facilityRepository!!.deleteAll()
            System.setProperty("forceClean", "false")
        }
        val amount: Long = facilityRepository!!.count();
        val create: Long = guaranteedAmount - amount;
        if (create > 0) {
            val facilityFactory = FacilityFactory()
            val facilities = facilityFactory.create(create)
            facilityRepository!!.saveAll(facilities)
        }
    }

}