package ro.developmentfactory.thedrones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.developmentfactory.thedrones.entity.DroneStatus;
import ro.developmentfactory.thedrones.repository.DroneStatusRepository;

import java.util.List;
import java.util.Objects;

@Service
public class DroneStatusServiceImpl implements DroneStatusService {

    @Autowired
    private DroneStatusRepository droneStatusRepository;

    //Read
    @Override
    public List<DroneStatus> fetchDroneStatusList() {
        return (List<DroneStatus>) droneStatusRepository.findAll();
    }

    //Save
    @Override
    public DroneStatus saveDroneStatus(DroneStatus droneStatus) {
        return droneStatusRepository.save(droneStatus);
    }

    //Update
    @Override
    public DroneStatus updateDroneStatus(DroneStatus droneStatus, Long idDroneStatus) {
        DroneStatus droneStatusDB = droneStatusRepository.findById(idDroneStatus).get();
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
            droneStatusDB.setDrone(droneStatus.getDrone());
        }
        return droneStatusRepository.save(droneStatusDB);
    }

    @Override
    public void deleteDroneStatusById(Long idDroneStatus) {
        droneStatusRepository.deleteById(idDroneStatus);
    }
}
