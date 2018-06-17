package net.fvogel.cdupdate.web

import net.fvogel.cdupdate.logic.FacilityManager
import net.fvogel.cdupdate.model.Facility
import net.fvogel.cdupdate.tasking.SchedulingManager
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
