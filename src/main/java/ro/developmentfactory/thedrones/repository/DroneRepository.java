package ro.developmentfactory.thedrones.repository;

import org.springframework.data.repository.CrudRepository;
import ro.developmentfactory.thedrones.entity.Drone;

public interface DroneRepository extends CrudRepository<Drone,Long> {

}
