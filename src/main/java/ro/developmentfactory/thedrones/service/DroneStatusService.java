package ro.developmentfactory.thedrones.service;

import ro.developmentfactory.thedrones.entity.DroneStatus;
import java.util.Optional;
import java.util.UUID;

public interface DroneStatusService {
    //Read operation
    Optional<DroneStatus> fetchDroneStatus(UUID idDrone);

    //Save operation
    void saveDroneStatus(DroneStatus droneStatus);

}
