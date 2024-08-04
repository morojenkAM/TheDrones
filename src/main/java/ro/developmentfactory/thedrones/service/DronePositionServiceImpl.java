package ro.developmentfactory.thedrones.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.developmentfactory.thedrones.controller.dto.DroneStatusResponse;
import ro.developmentfactory.thedrones.repository.DroneRepository;
import ro.developmentfactory.thedrones.repository.DroneStatusRepository;
import ro.developmentfactory.thedrones.repository.entity.Drone;
import ro.developmentfactory.thedrones.repository.entity.DroneStatus;

import java.util.UUID;

@Service
public class DronePositionServiceImpl implements DronePositionService {

    private final DroneStatusRepository droneStatusRepository;
    private final DroneRepository droneRepository;

    public DronePositionServiceImpl(DroneStatusRepository droneStatusRepository, DroneRepository droneRepository) {
        this.droneStatusRepository = droneStatusRepository;
        this.droneRepository = droneRepository;
    }

    @Override
    @Transactional
    public DroneStatusResponse turnDroneLeft(DroneStatusResponse droneStatusResponse) {
        UUID idDrone = droneStatusResponse.getIdDrone();
        Drone drone = droneRepository.findById(idDrone)
                .orElseThrow(() -> new EntityNotFoundException("Drone with id " + idDrone + " not found"));

        DroneStatus currentStatus = drone.getDroneStatusList().getFirst();
        currentStatus.setFacingDirection(currentStatus.getFacingDirection().turnLeft());

        droneStatusRepository.save(currentStatus);
        return convertToResponse(currentStatus);
    }

    @Override
    @Transactional
    public DroneStatusResponse turnDroneRight(DroneStatusResponse droneStatusResponse) {
        UUID idDrone = droneStatusResponse.getIdDrone();

        Drone drone = droneRepository.findById(idDrone)
                .orElseThrow(() -> new EntityNotFoundException("Drone with id " + idDrone + " not found"));

        DroneStatus currentStatus = drone.getDroneStatusList().getFirst();
        currentStatus.setFacingDirection(currentStatus.getFacingDirection().turnRight());

        droneStatusRepository.save(currentStatus);
        return convertToResponse(currentStatus);
    }

    @Override
    @Transactional
    public DroneStatusResponse moveForward(DroneStatusResponse droneStatusResponse) {
        DroneStatus droneStatus = droneStatusRepository.findById(droneStatusResponse.getIdDroneStatus())
                .orElseThrow(() -> new EntityNotFoundException("Drone status with id " + droneStatusResponse.getIdDroneStatus() + " not found"));

        int newX = droneStatus.getCurrentPositionX();
        int newY = droneStatus.getCurrentPositionY();

        switch (droneStatus.getFacingDirection()) {
            case N:
                newY = newY + 1;
                break;
            case S:
                newY = newY - 1;
                break;
            case E:
                newX = newX + 1;
                break;
            case W:
                newX = newX - 1;
                break;
        }

        if (newX < 0 || newX >= 10 || newY < 0 || newY >= 10) {
            throw new IllegalArgumentException("Drone cannot move because it’s at the edge of the field");
        }

        droneStatus.setCurrentPositionX(newX);
        droneStatus.setCurrentPositionY(newY);
        droneStatus.getDrone().setCountMove(droneStatus.getDrone().getCountMove() + 1);

        droneStatusRepository.save(droneStatus);
        return convertToResponse(droneStatus);
    }

    private DroneStatusResponse convertToResponse(DroneStatus droneStatus) {
        if (droneStatus.getDrone() == null) {
            throw new IllegalArgumentException("Drone must not be null");
        }

        DroneStatusResponse response = new DroneStatusResponse();
        response.setIdDroneStatus(droneStatus.getIdDroneStatus());
        response.setIdDrone(droneStatus.getDrone().getIdDrone());
        response.setCurrentPositionX(droneStatus.getCurrentPositionX());
        response.setCurrentPositionY(droneStatus.getCurrentPositionY());
        response.setFacingDirection(droneStatus.getFacingDirection());

        return response;
    }
}
