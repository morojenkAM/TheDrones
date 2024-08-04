package ro.developmentfactory.thedrones.service;

import ro.developmentfactory.thedrones.controller.dto.DroneStatusResponse;

public interface DronePositionService {

    DroneStatusResponse turnDroneLeft(DroneStatusResponse droneStatusResponse);

    DroneStatusResponse turnDroneRight(DroneStatusResponse droneStatusResponse);

    DroneStatusResponse moveForward(DroneStatusResponse droneStatusResponse);
}
