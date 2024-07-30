package ro.developmentfactory.thedrones.controller;


import org.springframework.web.bind.annotation.*;
import ro.developmentfactory.thedrones.repository.entity.DroneStatus;
import ro.developmentfactory.thedrones.service.DroneStatusService;

import java.util.UUID;


@RestController
@RequestMapping("/drones/status")
public class DroneStatusController {

    private final DroneStatusService droneStatusService;

    public DroneStatusController(DroneStatusService droneStatusService) {
        this.droneStatusService = droneStatusService;
    }

    //Get current status
    @GetMapping("/{idDrone}")
    public DroneStatus getDroneStatus(@PathVariable UUID idDrone) {
        return droneStatusService.fetchDroneStatus(idDrone);
    }
}
