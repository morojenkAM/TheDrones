package ro.developmentfactory.thedrones.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.developmentfactory.thedrones.controller.dto.DroneStatusResponse;
import ro.developmentfactory.thedrones.repository.DroneRepository;
import ro.developmentfactory.thedrones.repository.DroneStatusRepository;
import ro.developmentfactory.thedrones.repository.entity.Direction;
import ro.developmentfactory.thedrones.repository.entity.Drone;
import ro.developmentfactory.thedrones.repository.entity.DroneStatus;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DronePositionServiceImplTest {

    @Mock
    private DroneStatusRepository droneStatusRepository;

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DronePositionServiceImpl dronePositionService;

    private Drone drone;
    private DroneStatus droneStatus;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        droneStatus = new DroneStatus();
        droneStatus.setCurrentPositionX(5);
        droneStatus.setCurrentPositionY(5);
        droneStatus.setFacingDirection(Direction.N);

        drone = new Drone();
        drone.setIdDrone(UUID.randomUUID());
        drone.setDroneStatus(droneStatus);
        drone.setCountMove(0);

        droneStatus.setDrone(drone);
    }



    @Test
    @DisplayName("Turning Direction: Valid right turn should update direction")
    void turningDirection_ValidRightTurn_ShouldUpdateDirection() {
        //Given
        when(droneRepository.findById(any(UUID.class))).thenReturn(Optional.of(drone));

        DroneStatusResponse request = DroneStatusResponse.builder()
                .idDrone(drone.getIdDrone())
                .turnDirection(DroneStatusResponse.TurnDirection.RIGHT)
                .build();
//When
        DroneStatusResponse response = dronePositionService.turningDirection(request);
//Then
        assertEquals(Direction.E, response.getFacingDirection());
        verify(droneStatusRepository).save(droneStatus);
    }

    @Test
    @DisplayName("Turning Direction: Invalid drone ID should throw exception")
    void turningDirection_InvalidDroneId_ShouldThrowException() {
        //Given
        when(droneRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        DroneStatusResponse request = DroneStatusResponse.builder()
                .idDrone(UUID.randomUUID())
                .turnDirection(DroneStatusResponse.TurnDirection.RIGHT)
                .build();

        //When //Then
        assertThrows(EntityNotFoundException.class, () -> dronePositionService.turningDirection(request));
    }

    @Test
    @DisplayName("Move Forward: Valid move should update position")
    void moveForward_ValidMove_ShouldUpdatePosition() {
        //given
        when(droneRepository.findById(any(UUID.class))).thenReturn(Optional.of(drone));

        DroneStatusResponse request = DroneStatusResponse.builder()
                .idDrone(drone.getIdDrone())
                .build();
//when
        DroneStatusResponse response = dronePositionService.moveForward(request);
//then
        assertEquals(5, response.getCurrentPositionX());
        assertEquals(6, response.getCurrentPositionY());
        assertEquals(Direction.N, response.getFacingDirection());
        verify(droneStatusRepository).save(droneStatus);
    }

    @Test
    @DisplayName("Move Forward: Invalid drone ID should throw exception")
    void moveForward_InvalidDroneId_ShouldThrowException() {
        //given
        when(droneRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        DroneStatusResponse request = DroneStatusResponse.builder()
                .idDrone(UUID.randomUUID())
                .build();
//when //then
        assertThrows(EntityNotFoundException.class, () -> dronePositionService.moveForward(request));
    }

    @Test
    @DisplayName("Move Forward: Drone at edge should not update position")
    void moveForward_DroneAtEdge_ShouldNotUpdatePosition() {
        //given
        droneStatus.setCurrentPositionX(9);
        droneStatus.setCurrentPositionY(9);
        droneStatus.setFacingDirection(Direction.E);
        when(droneRepository.findById(any(UUID.class))).thenReturn(Optional.of(drone));

        DroneStatusResponse request = DroneStatusResponse.builder()
                .idDrone(drone.getIdDrone())
                .build();
        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dronePositionService.moveForward(request));

        //then
        assertEquals("Drone cannot move because it’s at the edge of the field", exception.getMessage());

        verify(droneStatusRepository, never()).save(any(DroneStatus.class));
    }
    @Test
    @DisplayName("Turning Direction: Null turn direction should throw exception")
    void turningDirection_NullTurnDirection_ShouldThrowException() {
        // Given
        when(droneRepository.findById(any(UUID.class))).thenReturn(Optional.of(drone));

        DroneStatusResponse request = DroneStatusResponse.builder()
                .idDrone(drone.getIdDrone())
                .turnDirection(null)
                .build();

        // When // Then
        assertThrows(IllegalArgumentException.class, () -> dronePositionService.turningDirection(request));
    }

    @Test
    @DisplayName("Move Forward: Multiple valid moves should update position correctly")
    void moveForward_MultipleMoves_ShouldUpdatePositionCorrectly() {
        // Given
        droneStatus.setCurrentPositionX(0);
        droneStatus.setCurrentPositionY(0);
        droneStatus.setFacingDirection(Direction.E);
        when(droneRepository.findById(any(UUID.class))).thenReturn(Optional.of(drone));

        DroneStatusResponse request = DroneStatusResponse.builder()
                .idDrone(drone.getIdDrone())
                .build();

        // When
        dronePositionService.moveForward(request); // Move to (1, 0)
        dronePositionService.moveForward(request); // Move to (2, 0)

        // Then
        assertEquals(2, droneStatus.getCurrentPositionX());
        assertEquals(0, droneStatus.getCurrentPositionY());
        assertEquals(Direction.E, droneStatus.getFacingDirection());
        assertEquals(2, drone.getCountMove());
    }

}
