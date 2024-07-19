package ro.developmentfactory.thedrones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.developmentfactory.thedrones.entity.Drone;
import ro.developmentfactory.thedrones.repository.DroneRepository;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Service
public class DroneServiceImpl implements DroneServcie {

    @Autowired
    private DroneRepository droneRepository;
    private DroneStatusService droneStatusService;

    //Read operation
    @Override public List<Drone> fetchDroneList(){
        return (List<Drone>) droneRepository.findAll();
    }

    @Override
    public Drone saveDrone(Drone drone) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        drone.setCreatedAt(currentTime);
        drone.setUpdatedAt(currentTime);
        return droneRepository.save(drone);
    }

    @Override
    public Drone updateDrone(Drone drone, Long droneID) {
        Drone droneDB = droneRepository.findById(droneID).get();

        if (Objects.nonNull(drone.getName()) && !"".equalsIgnoreCase(drone.getName())) {
            droneDB.setName(drone.getName());
        }
        if (Objects.nonNull(drone.getCountMove())) {
            droneDB.setCountMove(drone.getCountMove());
        }
        Date date = new Date(System.currentTimeMillis());
        droneDB.setUpdatedAt(new Timestamp(date.getTime()));

        return droneRepository.save(droneDB);
    }

    @Override
    public void deleteDrone(Long droneId) {
        droneRepository.deleteById(droneId);
    }

}
