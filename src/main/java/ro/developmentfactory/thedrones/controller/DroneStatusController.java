package ro.developmentfactory.thedrones.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.developmentfactory.thedrones.entity.DroneStatus;
import ro.developmentfactory.thedrones.DroneStatusService;

import java.util.List;

@RestController
@RequestMapping("/dronesStatus")
public class DroneStatusController {

    @Autowired
    private DroneStatusService droneStatusService;

    // Save
    @PostMapping
    public DroneStatus saveDroneStatus(@Valid @RequestBody DroneStatus droneStatus) {
        return droneStatusService.saveDroneStatus(droneStatus);
    }

    // Get all
    @GetMapping
    public List<DroneStatus> fetchAllDroneStatus() {
        return droneStatusService.fetchDroneStatusList();
    }

    // Update
    @PutMapping("/{idDroneStatus}")
    public DroneStatus updateDroneStatus(@RequestBody DroneStatus droneStatus, @PathVariable Long idDroneStatus) {
        return droneStatusService.updateDroneStatus(droneStatus, idDroneStatus);
    }

    // Delete
    @DeleteMapping("/{idDroneStatus}")
    public String deleteDroneStatus(@PathVariable Long idDroneStatus) {
        droneStatusService.deleteDroneStatusById(idDroneStatus);
        return "DroneStatus deleted";
    }
}
