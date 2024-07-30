package ro.developmentfactory.thedrones.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.developmentfactory.thedrones.repository.entity.Drone;
import ro.developmentfactory.thedrones.repository.entity.DroneStatus;
import ro.developmentfactory.thedrones.repository.DroneRepository;
import ro.developmentfactory.thedrones.repository.DroneStatusRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DronesStatusServiceImplTest {

    @Mock
    private DroneStatusRepository droneStatusRepository;

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DroneStatusServiceImpl droneStatusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Fetch drone status should return DroneStatus when ID exists")
    void fetchDroneStatus_WhenIdExists_ReturnsDroneStatus() {
        // Given
        UUID droneId = UUID.randomUUID();
        DroneStatus expectedDroneStatus = new DroneStatus();
        expectedDroneStatus.setIdDroneStatus(droneId);
        when(droneStatusRepository.findById(droneId)).thenReturn(Optional.of(expectedDroneStatus));

        // When
        DroneStatus actualDroneStatus = droneStatusService.fetchDroneStatus(droneId);

        // Then
        assertNotNull(actualDroneStatus, "DroneStatus should not be null");
        assertEquals(expectedDroneStatus, actualDroneStatus, "DroneStatus should match the expected value");
    }

    @Test
    @DisplayName("Fetch drone status should return empty when ID does not exist")
    void fetchDroneStatus_WhenIdDoesNotExist_ReturnsEmpty() {
        // Given
        UUID droneId = UUID.randomUUID();
        when(droneStatusRepository.findById(droneId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> droneStatusService.fetchDroneStatus(droneId),
                "Expected fetchDroneStatus() to throw IllegalArgumentException"
        );
        assertEquals("DroneStatus ot found for this ID", thrown.getMessage());

    }

    @Test
    @DisplayName("Save drone status should save when drone exists")
    void saveDroneStatus_WhenDroneExists_SavesDroneStatus() {
        // Given
        UUID droneId = UUID.randomUUID();
        Drone drone = new Drone();
        drone.setIdDrone(droneId);

        DroneStatus droneStatus = new DroneStatus();
        droneStatus.setDrone(drone);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        // When
        droneStatusService.saveDroneStatus(droneStatus);

        // Then
        verify(droneStatusRepository, times(1)).save(droneStatus);
    }

    @Test
    @DisplayName("Save drone status should throw exception if drone does not exist")
    void saveDroneStatus_WhenDroneDoesNotExist_ThrowsException() {
        // Given
        UUID droneId = UUID.randomUUID();
        DroneStatus droneStatus = new DroneStatus();
        droneStatus.setDrone(new Drone());
        droneStatus.getDrone().setIdDrone(droneId);

        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> droneStatusService.saveDroneStatus(droneStatus),
                "Expected saveDroneStatus() to throw IllegalArgumentException"
        );
        assertEquals("Drone with this ID does not exist.", thrown.getMessage());
    }

    @Test
    @DisplayName("Save drone status with null drone should throw exception")
    void saveDroneStatus_WhenDroneIsNull_ShouldThrowException() {
        // Given
        DroneStatus droneStatus = new DroneStatus();

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> droneStatusService.saveDroneStatus(droneStatus),
                "Drone must not be null");
        assertEquals("Drone must not be null", exception.getMessage());
    }




    @Test
    @DisplayName("Update drone status when it exists should update the status")
    void updateDroneStatus_WhenDroneStatusExists_UpdatesDroneStatus() {
        // Given
        UUID droneId = UUID.randomUUID();
        Drone drone = new Drone();
        drone.setIdDrone(droneId);

        DroneStatus existingDroneStatus = new DroneStatus();
        existingDroneStatus.setIdDroneStatus(droneId);
        existingDroneStatus.setDrone(drone);
        existingDroneStatus.setCurrentPositionX(1);

        DroneStatus updatedDroneStatus = new DroneStatus();
        updatedDroneStatus.setIdDroneStatus(droneId);
        updatedDroneStatus.setDrone(drone);
        updatedDroneStatus.setCurrentPositionX(2);

        when(droneStatusRepository.findById(droneId)).thenReturn(Optional.of(existingDroneStatus));
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        // When
        droneStatusService.saveDroneStatus(updatedDroneStatus);

        // Then
        verify(droneStatusRepository, times(1)).save(updatedDroneStatus);
        assertEquals(2, updatedDroneStatus.getCurrentPositionX(), "DroneStatus position should be updated");
    }



    @Test
    @DisplayName("Fetch drone status when repository returns empty should return empty optional")
    void fetchDroneStatus_WhenRepositoryReturnsEmpty_HandlesGracefully() {
        // Given
        UUID droneId = UUID.randomUUID();
        when(droneStatusRepository.findById(droneId)).thenReturn(Optional.empty());

        // When & Then
      IllegalArgumentException thrown = assertThrows(
              IllegalArgumentException.class,
              () -> droneStatusService.fetchDroneStatus(droneId),
              "Expected fetchDroneStatus() to throw IllegalArgumentException"
      );
      assertEquals("DroneStatus ot found for this ID", thrown.getMessage());
    }



}
