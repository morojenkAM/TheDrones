package ro.developmentfactory.thedrones.service;

import ro.developmentfactory.thedrones.controller.dto.DroneStatusResponse;
import ro.developmentfactory.thedrones.repository.entity.DroneStatus;
import java.util.UUID;

public interface DroneStatusService {
    //Read operation
   DroneStatusResponse fetchDroneStatus(UUID idDrone);

    //Save operation
    void saveDroneStatus(DroneStatus droneStatus);

    void deleteDroneStatus(UUID idDroneStatus);

}
