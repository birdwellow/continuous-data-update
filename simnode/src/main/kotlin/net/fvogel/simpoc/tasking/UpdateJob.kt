package net.fvogel.simpoc.tasking

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.SchedulerException

import net.fvogel.simpoc.logic.FacilityManager

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