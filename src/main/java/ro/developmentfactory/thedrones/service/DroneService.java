package ro.developmentfactory.thedrones.service;

import ro.developmentfactory.thedrones.controller.dto.DroneRequest;
import ro.developmentfactory.thedrones.controller.dto.DroneResponse;
import ro.developmentfactory.thedrones.repository.entity.Drone;


import java.util.List;
import java.util.UUID;

public interface DroneService {
    //Read operation
    List<Drone> fetchDroneList();

    //Save operation
    DroneResponse saveDrone(DroneRequest droneRequest);

    //Update operation
    DroneResponse updateNameDrone(DroneRequest droneRequest,UUID idDrone);

    void deleteDrone(UUID idDrone);
}
