package ro.developmentfactory.thedrones.repository;

import org.springframework.data.repository.CrudRepository;
import ro.developmentfactory.thedrones.entity.DroneStatus;

public interface DroneStatusRepository extends CrudRepository<DroneStatus,Long> {

}