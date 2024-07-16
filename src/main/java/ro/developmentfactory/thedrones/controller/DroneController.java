package ro.developmentfactory.thedrones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.developmentfactory.thedrones.entity.Drones;
import ro.developmentfactory.thedrones.service.DronesService;

import java.util.List;

@RestController
public class DroneController {

    @Autowired
    private DronesService droneService;

    @PatchMapping("/addDrone")
    public Drones addDrone(@RequestBody Drones drone){
        return droneService.saveDrone(drone);
    }
    @PatchMapping("/addDrones")
    public List<Drones> addDrones(@RequestBody List<Drones> drones){
        return droneService.saveDrones(drones);
    }
    @GetMapping("/drones")
    public List<Drones> findAllDrones() {
        return droneService.getDrones();
    }
    @GetMapping("/drone/id_drones/{id_drones}")
    public Drones findDroneById(@PathVariable ("id_drones") int id) {
        return droneService.getDronesById(id);
    }
    @GetMapping("/drone/name/{name}")
    public Drones findDroneByName(@PathVariable("name") String name) {
        return droneService.getDronesByName(name);
    }
    @PutMapping("/updateDrone")
    public Drones updateDrone(@RequestBody Drones drone){
        return droneService.updateDrones(drone);
    }
    @DeleteMapping("/delete/{id_drones}")
    public String deleteDrone( @PathVariable int id_drones) {
        return droneService.deleteDrones(id_drones);
    }
}

