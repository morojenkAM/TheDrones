package ro.developmentfactory.thedrones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.developmentfactory.thedrones.entity.Drones;

public interface DronesRepository extends JpaRepository<Drones, Integer> {
    Drones findByName(String name);
}
