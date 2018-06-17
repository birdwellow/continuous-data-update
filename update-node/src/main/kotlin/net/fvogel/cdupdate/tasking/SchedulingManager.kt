package net.fvogel.cdupdate.tasking

import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import net.fvogel.cdupdate.logic.FacilityManager
import org.quartz.*
import org.quartz.impl.StdSchedulerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class SchedulingManager {

    private var scheduler: Scheduler? = null
    private var jobDetail: JobDetail? = null

    @Autowired
    internal var facilityManager: FacilityManager? = null

    init {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler()

            jobDetail = JobBuilder
                    .newJob(UpdateJob::class.java)
                    .withIdentity("name", "group")
                    .build()

            val trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(5)
                            .repeatForever())
                    .build()

            scheduler!!.scheduleJob(jobDetail, trigger)
        } catch (e: SchedulerException) {
            log.printf("Error while creating scheduler: %s", e)
        }

    }

    fun start() {
        try {
            if (scheduler!!.isStarted) {
                log.print("Scheduler already started")
                return
            }

            scheduler!!.context.put("facilityManager", facilityManager)
            scheduler!!.start()
        } catch (e: SchedulerException) {
            // TODO
            log.printf("SchedulerException: %s", e)
        }

    }

    fun stop() {
        try {
            if (scheduler!!.isShutdown) {
                log.print("Scheduler not started!")
                return
            }
            scheduler!!.shutdown(false)
            log.print("Stopped")
        } catch (e: SchedulerException) {
            // TODO
            log.printf("SchedulerException: %s", e)
        }

    }

    fun toggle() {
        try {
            if (scheduler!!.isStarted) {
                stop()
            } else {
                start()
            }
        } catch (e: SchedulerException) {
            // TODO
            log.printf("SchedulerException: %s", e)
        }

    }
}