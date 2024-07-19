package ro.developmentfactory.thedrones.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.developmentfactory.thedrones.entity.Drone;
import ro.developmentfactory.thedrones.DroneServcie;

import java.util.List;

@RestController
public class DroneControler {

    @Autowired
    private DroneServcie droneService;

    //Save operation
    @PostMapping("/drones")
    public Drone saveDrone(
            @Valid @RequestBody Drone drone){
        return droneService.saveDrone(drone);
    }

    @GetMapping("/drones")
    public List<Drone> fetchDronesList() {
        return droneService.fetchDroneList();
    }

    //Update operation
    @PutMapping("/drones/{idDrones}")
    public Drone updateDrone(@RequestBody Drone drone,@PathVariable ("idDrones") Long idDrones){
        return droneService.updateDrone(drone,idDrones);
    }

    @DeleteMapping("/drones/{idDrones}")
    public String deleteDroneById(@PathVariable ("idDrones") Long idDrones){
        droneService.deleteDrone(idDrones);
        return "Drone deleted";
    }
}
