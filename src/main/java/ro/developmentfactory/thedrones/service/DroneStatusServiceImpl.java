package ro.developmentfactory.thedrones.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
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
    public DroneStatus fetchDroneStatus(UUID idDrone) {
        return droneStatusRepository.findById(idDrone)
                .orElseThrow(() -> new IllegalArgumentException("DroneStatus ot found for this ID"));
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
}
