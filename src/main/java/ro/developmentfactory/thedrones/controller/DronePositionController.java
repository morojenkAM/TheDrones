package ro.developmentfactory.thedrones.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.developmentfactory.thedrones.controller.dto.DroneStatusResponse;
import ro.developmentfactory.thedrones.service.DronePositionService;

import java.util.UUID;

@RestController
@RequestMapping("/drones/{id}")
public class DronePositionController {

    private final DronePositionService dronePositionService;
    private static final Logger log = LoggerFactory.getLogger(DronePositionController.class);

    public DronePositionController(DronePositionService dronePositionService) {
        this.dronePositionService = dronePositionService;
    }

    @PostMapping("/turn/L")
    public ResponseEntity<DroneStatusResponse> turnLeft(@PathVariable ("id") UUID idDrone) {
        log.debug("Received request to turn left for drone with id: {}", idDrone);
        DroneStatusResponse response = dronePositionService.turningDirection(
                DroneStatusResponse.builder()
                        .idDrone(idDrone)
                        .turnDirection(DroneStatusResponse.TurnDirection.LEFT)
                        .build());
        log.debug("Response from turn left: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/turn/R")
    public ResponseEntity<DroneStatusResponse> turnRight(@PathVariable ("id") UUID idDrone) {
        log.debug("Received request to turn right for drone with id: {}", idDrone);
        DroneStatusResponse response = dronePositionService.turningDirection(
                DroneStatusResponse.builder()
                        .idDrone(idDrone)
                        .turnDirection(DroneStatusResponse.TurnDirection.RIGHT)
                        .build()
        );
        log.debug("Response from turn right: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/goForward")
    public ResponseEntity<DroneStatusResponse> moveForward(@PathVariable ("id") UUID idDrone) {
        log.debug("Received request to move forward: {}", idDrone);
        DroneStatusResponse response = dronePositionService.moveForward(new DroneStatusResponse(idDrone));
        log.debug("Response from move forward: {}", response);
        return ResponseEntity.ok(response);
    }
}
