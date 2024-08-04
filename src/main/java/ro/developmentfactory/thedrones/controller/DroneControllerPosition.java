package ro.developmentfactory.thedrones.controller;

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

    public DroneControllerPosition(DronePositionService dronePositionService) {
        this.dronePositionService = dronePositionService;
    }

    @PostMapping("/turnLeft")
    public ResponseEntity<DroneStatusResponse> turnLeft(@RequestBody DroneStatusResponse droneStatusResponse) {
        DroneStatusResponse response = dronePositionService.turnDroneLeft(droneStatusResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/turnRight")
    public ResponseEntity<DroneStatusResponse> turnRight(@RequestBody DroneStatusResponse droneStatusResponse) {
        DroneStatusResponse response = dronePositionService.turnDroneRight(droneStatusResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forward")
    public ResponseEntity<DroneStatusResponse> moveForward(@RequestBody DroneStatusResponse droneStatusResponse) {
        DroneStatusResponse response = dronePositionService.moveForward(droneStatusResponse);
        return ResponseEntity.ok(response);
    }
}
