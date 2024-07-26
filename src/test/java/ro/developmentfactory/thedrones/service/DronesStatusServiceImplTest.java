package ro.developmentfactory.thedrones.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.developmentfactory.thedrones.entity.Drone;
import ro.developmentfactory.thedrones.entity.DroneStatus;
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
        Optional<DroneStatus> actualDroneStatus = droneStatusService.fetchDroneStatus(droneId);

        // Then
        assertTrue(actualDroneStatus.isPresent(), "DroneStatus should be present");
        assertEquals(expectedDroneStatus, actualDroneStatus.get(), "DroneStatus should match the expected value");
    }

    @Test
    @DisplayName("Fetch drone status should return empty when ID does not exist")
    void fetchDroneStatus_WhenIdDoesNotExist_ReturnsEmpty() {
        // Given
        UUID droneId = UUID.randomUUID();
        when(droneStatusRepository.findById(droneId)).thenReturn(Optional.empty());

        // When
        Optional<DroneStatus> actualDroneStatus = droneStatusService.fetchDroneStatus(droneId);

        // Then
        assertFalse(actualDroneStatus.isPresent(), "DroneStatus should not be present");
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
    @DisplayName("Save drone status when drone is null should still save the status")
    void saveDroneStatus_WhenDroneIsNull_SavesDroneStatusWithoutDrone() {
        // Given
        DroneStatus droneStatus = new DroneStatus();

        // When
        droneStatusService.saveDroneStatus(droneStatus);

        // Then
        verify(droneStatusRepository, times(1)).save(droneStatus);
    }
    @Test
    @DisplayName("Save drone status when drone is null should save the status")
    void saveDroneStatus_WhenDroneStatusHasNullDrone_SavesDroneStatus() {
        // Given
        DroneStatus droneStatus = new DroneStatus();
        // Drone is null

        // When
        droneStatusService.saveDroneStatus(droneStatus);

        // Then
        verify(droneStatusRepository, times(1)).save(droneStatus);
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
    @DisplayName("Save drone status when repository throws exception should handle it gracefully")
    void saveDroneStatus_WhenRepositoryThrowsException_HandlesGracefully() {
        // Given
        DroneStatus droneStatus = new DroneStatus();
        UUID droneId = UUID.randomUUID();
        Drone drone = new Drone();
        drone.setIdDrone(droneId);
        droneStatus.setDrone(drone);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        doThrow(new RuntimeException("Database error")).when(droneStatusRepository).save(droneStatus);

        // When & Then
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> droneStatusService.saveDroneStatus(droneStatus),
                "Expected saveDroneStatus() to throw RuntimeException due to database error"
        );
        assertEquals("Database error", thrown.getMessage());
    }

    @Test
    @DisplayName("Fetch drone status when repository returns empty should return empty optional")
    void fetchDroneStatus_WhenRepositoryReturnsEmpty_HandlesGracefully() {
        // Given
        UUID droneId = UUID.randomUUID();
        when(droneStatusRepository.findById(droneId)).thenReturn(Optional.empty());

        // When
        Optional<DroneStatus> actualDroneStatus = droneStatusService.fetchDroneStatus(droneId);

        // Then
        assertFalse(actualDroneStatus.isPresent(), "DroneStatus should not be present");
    }



}
