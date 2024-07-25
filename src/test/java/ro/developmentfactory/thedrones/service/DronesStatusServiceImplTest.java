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

class DroneStatusServiceImplTest {

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
    @DisplayName("fetchDroneStatus_WhenIdExists_ReturnsDroneStatus")
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
    @DisplayName("fetchDroneStatus_WhenIdDoesNotExist_ReturnsEmpty")
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
    @DisplayName("saveDroneStatus_WhenDroneExists_SavesDroneStatus")
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
    @DisplayName("saveDroneStatus_WhenDroneDoesNotExist_ThrowsException")
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
    @DisplayName("saveDroneStatus_WhenDroneIsNull_SavesDroneStatusWithoutDrone")
    void saveDroneStatus_WhenDroneIsNull_SavesDroneStatusWithoutDrone() {
        // Given
        DroneStatus droneStatus = new DroneStatus();

        // When
        droneStatusService.saveDroneStatus(droneStatus);

        // Then
        verify(droneStatusRepository, times(1)).save(droneStatus);
    }
    @Test
    @DisplayName("saveDroneStatus_WhenDroneStatusHasNullDrone_SavesDroneStatus")
    void saveDroneStatus_WhenDroneStatusHasNullDrone_SavesDroneStatus() {
        // Given
        DroneStatus droneStatus = new DroneStatus();
        // Drone is null

        // When
        droneStatusService.saveDroneStatus(droneStatus);

        // Then
        verify(droneStatusRepository, times(1)).save(droneStatus);
    }




}
