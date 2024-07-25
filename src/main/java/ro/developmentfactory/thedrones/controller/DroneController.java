package ro.developmentfactory.thedrones.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ro.developmentfactory.thedrones.dto.DroneRequest;
import ro.developmentfactory.thedrones.dto.DroneResponse;
import ro.developmentfactory.thedrones.entity.Drone;
import ro.developmentfactory.thedrones.service.DroneService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drones")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    //Save operation
    @PostMapping
    public DroneResponse saveDrone(@Valid @RequestBody DroneRequest droneRequest){
        return droneService.saveDrone(droneRequest);
    }

    @GetMapping("/drones")
    public List<DroneResponse> fetchDronesList() {
        return droneService.fetchDroneList().stream()
                .map(drone -> DroneResponse.builder()
                .idDrone(drone.getIdDrone())
                .name(drone.getName())
                .countMove(drone.getCountMove())
                .createdAt(drone.getCreatedAt())
                .updatedAt(drone.getUpdatedAt())
                .build())
        .collect(Collectors.toList());
    }

    //Update operation
    @PutMapping("/drone/{idDrones}")
    public DroneResponse updateDrone(@RequestBody Drone drone,@PathVariable ("idDrones") UUID idDrones){
        DroneRequest droneRequest = DroneRequest.builder()
                .name(drone.getName())
                .build();
        return droneService.updateNameDrone(droneRequest,idDrones);
    }

    @PutMapping("/drone/countMove/{idDrones}")
    public DroneResponse updateDroneCountMove(@RequestBody Drone drone,@PathVariable ("idDrones") UUID idDrones){
        return droneService.updateCountMove(idDrones,drone.getCountMove());
    }

    @DeleteMapping("/drones/{idDrones}")
    public String deleteDroneById(@PathVariable ("idDrones") UUID idDrones){
        droneService.deleteDrone(idDrones);
        return "Drone deleted";
    }
}
