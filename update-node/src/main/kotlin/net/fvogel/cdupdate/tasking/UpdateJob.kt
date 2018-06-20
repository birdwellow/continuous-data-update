package net.fvogel.cdupdate.tasking

import net.fvogel.cdupdate.logic.FacilityManager
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.SchedulerException

class UpdateJob : Job {

    private var facilityManager: FacilityManager? = null

    @Throws(JobExecutionException::class)
    override fun execute(context: JobExecutionContext) {
        try {
            if (facilityManager == null) {
                facilityManager = context.scheduler.context["facilityManager"] as FacilityManager
            }
            facilityManager!!.update()
        } catch (e: SchedulerException) {
            // TODO
            println("SchedulerException$e")
        }

    }
}