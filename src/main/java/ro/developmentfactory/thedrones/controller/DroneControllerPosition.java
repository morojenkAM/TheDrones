package ro.developmentfactory.thedrones.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.developmentfactory.thedrones.controller.dto.DroneStatusResponse;
import ro.developmentfactory.thedrones.service.DronePositionService;

@RestController
@RequestMapping("/drones/status")
public class DroneControllerPosition {

    private final DronePositionService dronePositionService;
    private static final Logger log = LoggerFactory.getLogger(DroneControllerPosition.class);

    public DroneControllerPosition(DronePositionService dronePositionService) {
        this.dronePositionService = dronePositionService;
    }

    @PostMapping("/turnLeft")
    public ResponseEntity<DroneStatusResponse> turnLeft(@RequestBody DroneStatusResponse droneStatusResponse) {
        log.debug("Received request to turn left: {}", droneStatusResponse);
        DroneStatusResponse response = dronePositionService.turnDroneLeft(droneStatusResponse);
        log.debug("Response from turn left: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/turnRight")
    public ResponseEntity<DroneStatusResponse> turnRight(@RequestBody DroneStatusResponse droneStatusResponse) {
        log.debug("Received request to turn right: {}", droneStatusResponse);
        DroneStatusResponse response = dronePositionService.turnDroneRight(droneStatusResponse);
        log.debug("Response from turn right: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forward")
    public ResponseEntity<DroneStatusResponse> moveForward(@RequestBody DroneStatusResponse droneStatusResponse) {
        log.debug("Received request to move forward: {}", droneStatusResponse);
        DroneStatusResponse response = dronePositionService.moveForward(droneStatusResponse);
        log.debug("Response from move forward: {}", response);
        return ResponseEntity.ok(response);
    }
}
