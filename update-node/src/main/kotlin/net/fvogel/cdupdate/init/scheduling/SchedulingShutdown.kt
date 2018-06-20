package net.fvogel.cdupdate.init.scheduling

import net.fvogel.cdupdate.tasking.SchedulingManager
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy


@Component
internal class SchedulingShutdown(private val schedulingManager: SchedulingManager) {

    @PreDestroy
    fun preDestroy() {
        schedulingManager.stop()
    }

}
