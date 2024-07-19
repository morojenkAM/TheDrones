package ro.developmentfactory.thedrones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.developmentfactory.thedrones.entity.Drone;
import ro.developmentfactory.thedrones.entity.DroneStatus;
import ro.developmentfactory.thedrones.repository.DroneRepository;
import ro.developmentfactory.thedrones.repository.DroneStatusRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DroneStatusServiceImpl implements DroneStatusService {

    @Autowired
    private DroneStatusRepository droneStatusRepository;

    @Autowired
    private DroneRepository droneRepository;

    // Read
    @Override
    public List<DroneStatus> fetchDroneStatusList() {
        return (List<DroneStatus>) droneStatusRepository.findAll();
    }

    // Save
    @Override
    public DroneStatus saveDroneStatus(DroneStatus droneStatus) {
        if (droneStatus.getDrone() != null) {
            Optional<Drone> droneOptional = droneRepository.findById(droneStatus.getDrone().getIdDrone());
            if (droneOptional.isPresent()) {
                droneStatus.setDrone(droneOptional.get());
            } else {
                throw new IllegalArgumentException("Drone with ID " + droneStatus.getDrone().getIdDrone() + " does not exist.");
            }
        }
        return droneStatusRepository.save(droneStatus);
    }

    // Update
    @Override
    public DroneStatus updateDroneStatus(DroneStatus droneStatus, Long idDroneStatus) {
        DroneStatus droneStatusDB = droneStatusRepository.findById(idDroneStatus).orElseThrow(() -> new IllegalArgumentException("DroneStatus not found"));

        if (Objects.nonNull(droneStatus.getCurrentPositionX())) {
            droneStatusDB.setCurrentPositionX(droneStatus.getCurrentPositionX());
        }
        if (Objects.nonNull(droneStatus.getCurrentPositionY())) {
            droneStatusDB.setCurrentPositionY(droneStatus.getCurrentPositionY());
        }
        if (Objects.nonNull(droneStatus.getFacingDirection())) {
            droneStatusDB.setFacingDirection(droneStatus.getFacingDirection());
        }
        if (Objects.nonNull(droneStatus.getDrone())) {
            Optional<Drone> droneOptional = droneRepository.findById(droneStatus.getDrone().getIdDrone());
            if (droneOptional.isPresent()) {
                droneStatusDB.setDrone(droneOptional.get());
            } else {
                throw new IllegalArgumentException("Drone with ID " + droneStatus.getDrone().getIdDrone() + " does not exist.");
            }
        }
        return droneStatusRepository.save(droneStatusDB);
    }

    @Override
    public void deleteDroneStatusById(Long idDroneStatus) {
        droneStatusRepository.deleteById(idDroneStatus);
    }
}
