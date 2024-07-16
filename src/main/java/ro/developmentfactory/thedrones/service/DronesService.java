package ro.developmentfactory.thedrones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.developmentfactory.thedrones.entity.Drones;
import ro.developmentfactory.thedrones.repository.DronesRepository;

import java.util.List;

@Service
public class DronesService {

    @Autowired
    private DronesRepository repository;

    public Drones saveDrone(Drones drone){
        return repository.save(drone);
    }

    public List<Drones> saveDrones(List<Drones> drones){
        return repository.saveAll(drones);
    }

    public List<Drones> getDrones() {
        return repository.findAll();
    }

    public Drones getDronesById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Drones getDronesByName(String name) {
        return repository.findByName(name);
    }

    public String deleteDrones(int id) {
        repository.deleteById(id);
        return "Drone removed! " + id;
    }

    public Drones updateDrones(Drones drone){
        Drones existingDrone = repository.findById(drone.getId_drone()).orElse(null);
        if (existingDrone != null) {
            existingDrone.setName(drone.getName());
            existingDrone.setCount_move(drone.getCount_move());
            existingDrone.setCreated_at(drone.getCreated_at());
            existingDrone.setUpdated_at(drone.getUpdated_at());
            return repository.save(existingDrone);
        }
        return null;
    }
}
