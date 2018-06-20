package net.fvogel.cdupdate.init.scheduling

import net.fvogel.cdupdate.tasking.SchedulingManager
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
@Profile("startSchedule")
internal class SchedulingInitializer(private val schedulingManager: SchedulingManager) {

    @PostConstruct()
    fun postContruct() {
        schedulingManager.start()
    }

}
