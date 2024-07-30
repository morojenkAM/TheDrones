package ro.developmentfactory.thedrones.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.developmentfactory.thedrones.repository.entity.Direction;
import ro.developmentfactory.thedrones.repository.entity.Drone;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DroneStatusResponse {
    private UUID idDroneStatus;
    private Integer currentPositionX;
    private Integer currentPositionY;
    private Direction facingDirection;
    private Drone drone;
}


