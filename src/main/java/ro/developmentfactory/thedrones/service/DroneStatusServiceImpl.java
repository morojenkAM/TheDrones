package ro.developmentfactory.thedrones.service;

import org.springframework.stereotype.Service;
import ro.developmentfactory.thedrones.entity.Drone;
import ro.developmentfactory.thedrones.entity.DroneStatus;
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
    public Optional<DroneStatus> fetchDroneStatus(UUID idDrone) {
        return droneStatusRepository.findById(idDrone);
    }

    // Save
    @Override
    public void saveDroneStatus(DroneStatus droneStatus) {
        if (droneStatus.getDrone() != null) {
            Optional<Drone> droneOptional = droneRepository.findById(droneStatus.getDrone().getIdDrone());
            if (droneOptional.isPresent()) {
                droneStatus.setDrone(droneOptional.get());
            } else {
                throw new IllegalArgumentException("Drone with this ID does not exist.");
            }
        }
        droneStatusRepository.save(droneStatus);
    }


}
