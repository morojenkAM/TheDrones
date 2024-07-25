package ro.developmentfactory.thedrones.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ro.developmentfactory.thedrones.dto.DroneRequest;
import ro.developmentfactory.thedrones.dto.DroneResponse;
import ro.developmentfactory.thedrones.entity.Direction;
import ro.developmentfactory.thedrones.entity.Drone;
import ro.developmentfactory.thedrones.entity.DroneStatus;
import ro.developmentfactory.thedrones.repository.DroneRepository;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final DroneStatusService droneStatusService;

    public DroneServiceImpl(DroneRepository droneRepository, DroneStatusService droneStatusService) {
        this.droneRepository = droneRepository;
        this.droneStatusService = droneStatusService;
    }

    //Read operation
    @Override
    public List<Drone> fetchDroneList(){
        return (List<Drone>) droneRepository.findAll();
    }

    @Override
    public DroneResponse saveDrone(DroneRequest droneRequest) {
        OffsetDateTime currentTime = OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        Drone drone = Drone.builder()
                .name(droneRequest.getName())
                .countMove(droneRequest.getCountMove())
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();

        Drone savedDrone = droneRepository.save(drone);

        DroneStatus droneStatus = DroneStatus.builder()
                .drone(savedDrone)
                .currentPositionX(0)
                .currentPositionY(0)
                .facingDirection(Direction.N)
                .build();
        droneStatusService.saveDroneStatus(droneStatus);
        return convertToResponse(savedDrone);
    }

    @Override
    public DroneResponse updateNameDrone(DroneRequest droneRequest, UUID idDrone){
        Drone droneDB = droneRepository.findById(idDrone).orElseThrow(() ->
        new EntityNotFoundException("Drone with id not found"));

        if (Objects.nonNull(droneRequest.getName()) && !"".equalsIgnoreCase(droneRequest.getName())) {
            droneDB.setName(droneRequest.getName());
        }
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        droneDB.setUpdatedAt(now);
        Drone updatedDrone = droneRepository.save(droneDB);
        return convertToResponse(updatedDrone);
    }

    @Override
    public DroneResponse updateCountMove(UUID idDrone, int newCountMove) {
        Drone droneDB = droneRepository.findById(idDrone).orElseThrow(() ->
                new EntityNotFoundException("Drone with id not found"));

        droneDB.setCountMove(newCountMove);
        Drone updatedDrone = droneRepository.save(droneDB);
        return convertToResponse(updatedDrone);
    }

    @Override
    public void deleteDrone(UUID idDrone) {
        if (!droneRepository.existsById(idDrone)) {
            throw new EntityNotFoundException("Drone with id not found");
        }
        droneRepository.deleteById(idDrone);
    }

    private DroneResponse convertToResponse(Drone drone){
        return DroneResponse.builder()
                .idDrone(drone.getIdDrone())
                .name(drone.getName())
                .countMove(drone.getCountMove())
                .createdAt(drone.getCreatedAt())
                .updatedAt(drone.getUpdatedAt())
                .build();
    }
}
