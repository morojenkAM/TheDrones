package ro.developmentfactory.thedrones.service;

import org.springframework.transaction.annotation.Transactional;
import ro.developmentfactory.thedrones.controller.dto.DroneStatusResponse;


public interface DronePositionService {

    @Transactional
    DroneStatusResponse turningDirection(DroneStatusResponse droneStatusResponse);

    @Transactional
    DroneStatusResponse moveForward(DroneStatusResponse droneStatusResponse);

}
