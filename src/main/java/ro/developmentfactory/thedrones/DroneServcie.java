package ro.developmentfactory.thedrones;

import ro.developmentfactory.thedrones.entity.Drone;
import java.util.List;

public interface DroneServcie {
    //Read operation
    List<Drone> fetchDroneList();

    //Save operation
    Drone saveDrone(Drone drone);

    //Update operation
    Drone updateDrone(Drone drone,Long droneID);

    //Delete operation
    void deleteDrone(Long droneId);
}
