package ro.developmentfactory.thedrones.repository;

import org.springframework.data.repository.CrudRepository;
import ro.developmentfactory.thedrones.repository.entity.Drone;

import java.util.UUID;


public interface DroneRepository extends CrudRepository<Drone,UUID> {


}
