package ro.developmentfactory.thedrones.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.developmentfactory.thedrones.controller.dto.DroneStatusResponse;
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
    public ResponseEntity<DroneStatusResponse> getDroneStatus(@PathVariable UUID idDrone) {
        DroneStatusResponse response = droneStatusService.fetchDroneStatus(idDrone);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> saveDroneStatus(@RequestBody DroneStatus droneStatus) {
        droneStatusService.saveDroneStatus(droneStatus);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idDroneStatus}")
    public ResponseEntity<Void> deleteDroneStatus(@PathVariable UUID idDroneStatus) {
        droneStatusService.deleteDroneStatus(idDroneStatus);
        return ResponseEntity.noContent().build();
    }
}
