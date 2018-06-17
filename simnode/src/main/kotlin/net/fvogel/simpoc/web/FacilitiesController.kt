package net.fvogel.simpoc.web

import net.fvogel.simpoc.logic.FacilityManager
import net.fvogel.simpoc.model.Facility
import net.fvogel.simpoc.tasking.SchedulingManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/facilities")
class FacilitiesController(val schedulingManager: SchedulingManager,
                           val facilityManager: FacilityManager ) {

    @GetMapping("/")
    fun facilities() : List<Facility> {
        return facilityManager.facilities;
    }

    @GetMapping("/toggle")
    fun toggle() {
        schedulingManager.toggle();
    }

}
