package ro.developmentfactory.thedrones;

import ro.developmentfactory.thedrones.entity.DroneStatus;

import java.util.List;

public interface DroneStatusService {
    //Read operation
    List<DroneStatus> fetchDroneStatusList();

    //Save operation
    DroneStatus saveDroneStatus(DroneStatus droneStatus);

    //Update operation
    DroneStatus updateDroneStatus(DroneStatus droneStatus,Long idDroneStatus);

    //Delete operation
    void deleteDroneStatusById(Long idDroneStatus);
}
