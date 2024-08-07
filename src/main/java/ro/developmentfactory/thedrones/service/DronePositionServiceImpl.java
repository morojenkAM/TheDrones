package ro.developmentfactory.thedrones.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.developmentfactory.thedrones.controller.dto.DroneStatusResponse;
import ro.developmentfactory.thedrones.repository.DroneRepository;
import ro.developmentfactory.thedrones.repository.DroneStatusRepository;
import ro.developmentfactory.thedrones.repository.entity.Direction;
import ro.developmentfactory.thedrones.repository.entity.Drone;
import ro.developmentfactory.thedrones.repository.entity.DroneStatus;

import java.util.UUID;

@Service
public class DronePositionServiceImpl implements DronePositionService {

    private final DroneStatusRepository droneStatusRepository;
    private final DroneRepository droneRepository;
    private static final Logger log = LoggerFactory.getLogger(DronePositionServiceImpl.class);

    public DronePositionServiceImpl(DroneStatusRepository droneStatusRepository, DroneRepository droneRepository) {
        this.droneStatusRepository = droneStatusRepository;
        this.droneRepository = droneRepository;
    }

    @Override
    @Transactional
    public DroneStatusResponse turningDirection(DroneStatusResponse droneStatusResponse) {
        UUID idDrone = droneStatusResponse.getIdDrone();

        Drone drone = droneRepository.findById(idDrone)
                .orElseThrow(() -> new EntityNotFoundException("Drone with id " + idDrone + " not found"));

        if (droneStatusResponse.getTurnDirection() == null) {
            throw new IllegalArgumentException("Turn direction cannot be null");
        }

        DroneStatus currentStatus = drone.getDroneStatus();

        Direction newDirection;

        if (droneStatusResponse.getTurnDirection() == DroneStatusResponse.TurnDirection.RIGHT) {
            newDirection = currentStatus.getFacingDirection().turnRight();
        } else {
            newDirection = currentStatus.getFacingDirection().turnLeft();
        }

        currentStatus.setFacingDirection(newDirection);

        droneStatusRepository.save(currentStatus);
        return convertToResponse(currentStatus);
    }

    @Override
    public DroneStatusResponse moveForward(DroneStatusResponse droneStatusResponse) {
        UUID idDrone = droneStatusResponse.getIdDrone();
        log.debug("Moving forward for drone: {}", idDrone);

        Drone drone = droneRepository.findById(idDrone)
                .orElseThrow(() -> new EntityNotFoundException("Drone with id " + idDrone + " not found"));

        DroneStatus droneStatus = drone.getDroneStatus();
        if (droneStatus == null) {
            throw new IllegalArgumentException("DroneStatus must not be null");
        }
        validateMove(droneStatus);
        updatePosition(droneStatus);

        droneStatusRepository.save(droneStatus);
        log.debug("Drone status updated successfully: {}", droneStatus);

        return convertToResponse(droneStatus);
    }

    private void updatePosition(DroneStatus droneStatus) {
        Position newPosition = calculateNewPosition(droneStatus);
        int newX = newPosition.getX();
        int newY = newPosition.getY();

        if(newX >= 0 && newX < 10 && newY >= 0 && newY < 10) {
            droneStatus.setCurrentPositionX(newX);
            droneStatus.setCurrentPositionY(newY);
            droneStatus.getDrone().setCountMove(droneStatus.getDrone().getCountMove() + 1);
            log.debug("New position for drone:({}, {})", newX, newY);
        }else {
            log.error("Drone cannot move because it's a Drone cannot move because it's at the edge of the field. Current position: ({}, {}), New position: ({}, {})",
                    droneStatus.getCurrentPositionX(), droneStatus.getCurrentPositionY(), newX, newY);
        }
    }


    private void validateMove(DroneStatus droneStatus) {
        Position newPosition = calculateNewPosition(droneStatus);
        int newX = newPosition.getX();
        int newY = newPosition.getY();

        if(newX < 0 ||  newX >= 10 || newY < 0 || newY >= 10) {
            throw new IllegalArgumentException("Drone cannot move because it’s at the edge of the field");
        }
    }


    private Position calculateNewPosition(DroneStatus droneStatus) {
        int x = droneStatus.getCurrentPositionX();
        int y = droneStatus.getCurrentPositionY();

        switch (droneStatus.getFacingDirection()) {
            case N:
                y += 1;
            break;

            case E:
                x += 1;
            break;

            case S:
                y -= 1;
            break;

            case W:
                x -= 1;
            break;
        }
        return new Position(x, y);
    }


    private DroneStatusResponse convertToResponse(DroneStatus droneStatus) {
        if (droneStatus.getDrone() == null) {
            throw new IllegalArgumentException("Drone must not be null");
        }

        DroneStatusResponse response = DroneStatusResponse.builder()
                .idDrone(droneStatus.getDrone().getIdDrone())
                .currentPositionX(droneStatus.getCurrentPositionX())
                .currentPositionY(droneStatus.getCurrentPositionY())
                .facingDirection(droneStatus.getFacingDirection())
                .build();

        log.debug("Converted drone status to response: {}", response);
        return response;
    }
}
