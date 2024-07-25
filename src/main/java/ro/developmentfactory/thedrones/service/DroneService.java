package ro.developmentfactory.thedrones.service;

import ro.developmentfactory.thedrones.dto.DroneRequest;
import ro.developmentfactory.thedrones.dto.DroneResponse;
import ro.developmentfactory.thedrones.entity.Drone;


import java.util.List;
import java.util.UUID;

public interface DroneService {
    //Read operation
    List<Drone> fetchDroneList();

    //Save operation
    DroneResponse saveDrone(DroneRequest droneRequest);

    //Update operation
    DroneResponse updateNameDrone(DroneRequest droneRequest,UUID idDrone);

    DroneResponse updateCountMove(UUID idDrone,int newCountMove);

    void deleteDrone(UUID idDrone);
}
