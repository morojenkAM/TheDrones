package ro.developmentfactory.thedrones.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ro.developmentfactory.thedrones.controller.dto.DroneStatusResponse;
import ro.developmentfactory.thedrones.repository.entity.Drone;
import ro.developmentfactory.thedrones.repository.entity.DroneStatus;
import ro.developmentfactory.thedrones.repository.DroneRepository;
import ro.developmentfactory.thedrones.repository.DroneStatusRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class DroneStatusServiceImpl implements DroneStatusService {

    private final DroneStatusRepository droneStatusRepository;
    private final DroneRepository droneRepository;

    public DroneStatusServiceImpl(DroneStatusRepository droneStatusRepository, DroneRepository droneRepository) {
        this.droneStatusRepository = droneStatusRepository;
        this.droneRepository = droneRepository;
    }

    // Read
    @Override
    public DroneStatusResponse fetchDroneStatus(UUID idDrone) {
        Drone drone = droneRepository.findById(idDrone)
                .orElseThrow(() -> new IllegalArgumentException("Drone not found for this ID"));

        if (drone.getDroneStatusList() == null || drone.getDroneStatusList().isEmpty()) {
            throw new IllegalArgumentException("No DroneStatus found for this Drone");
        }

        DroneStatus droneStatus = drone.getDroneStatusList().getFirst();

        return convertToResponse(droneStatus);
    }



    // Save
    @Override
    public void saveDroneStatus(DroneStatus droneStatus) {
        if (droneStatus.getDrone() == null) {
            throw new IllegalArgumentException("Drone must not be null");
        }

        Optional<Drone> droneOptional = droneRepository.findById(droneStatus.getDrone().getIdDrone());
        if (droneOptional.isPresent()) {
            droneStatus.setDrone(droneOptional.get());
        } else {
            throw new IllegalArgumentException("Drone with this ID does not exist.");
        }

        droneStatusRepository.save(droneStatus);
    }

    @Override
    public void deleteDroneStatus(UUID idDroneStatus) {
        if (!droneStatusRepository.existsById(idDroneStatus)) {
            throw new EntityNotFoundException("DroneStatus not found for this ID");
        }
        droneStatusRepository.deleteById(idDroneStatus);
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
