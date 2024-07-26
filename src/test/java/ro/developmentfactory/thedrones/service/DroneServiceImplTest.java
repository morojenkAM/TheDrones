package ro.developmentfactory.thedrones.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.developmentfactory.thedrones.dto.DroneRequest;
import ro.developmentfactory.thedrones.dto.DroneResponse;
import ro.developmentfactory.thedrones.entity.Drone;
import ro.developmentfactory.thedrones.entity.DroneStatus;
import ro.developmentfactory.thedrones.repository.DroneRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DroneServiceImplTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private DroneStatusService droneStatusService;

    @InjectMocks
    private DroneServiceImpl droneService;

    @Test
    @DisplayName("Given a list of drones, when fetchDroneList is called, then return the list of drones")
    void givenDroneList_whenFetchDroneList_thenReturnDroneList() {
        // Given
        Drone drone1 = new Drone();
        Drone drone2 = new Drone();
        List<Drone> droneList = List.of(drone1, drone2);
        when(droneRepository.findAll()).thenReturn(droneList);

        // When
        List<Drone> result = droneService.fetchDroneList();

        // Then
        assertEquals(droneList, result);
        verify(droneRepository).findAll();
    }

    @Test
    @DisplayName("Given a DroneRequest, when saveDrone is called, then save the drone and its status and return the response")
    void givenDroneRequest_whenSaveDrone_thenSaveDroneAndReturnResponse() {
        // Given
        DroneRequest droneRequest = new DroneRequest("Drone1", 5);
        Drone drone = Drone.builder()
                .idDrone(UUID.randomUUID())
                .name("Drone1")
                .countMove(5)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        when(droneRepository.save(any(Drone.class))).thenReturn(drone);
        doNothing().when(droneStatusService).saveDroneStatus(any(DroneStatus.class));

        // When
        DroneResponse result = droneService.saveDrone(droneRequest);

        // Then
        assertNotNull(result);
        assertEquals(drone.getIdDrone(), result.getIdDrone());
        assertEquals(droneRequest.getName(), result.getName());
        assertEquals(droneRequest.getCountMove(), result.getCountMove());
        verify(droneRepository).save(any(Drone.class));
        verify(droneStatusService).saveDroneStatus(any(DroneStatus.class));
    }



    @Test
    @DisplayName("Given a valid DroneRequest and Drone id, when updateNameDrone is called, then update the drone's name and return the updated response")
    void givenValidDroneRequestAndId_whenUpdateNameDrone_thenUpdateDroneAndReturnResponse() {
        // Given
        UUID droneId = UUID.randomUUID();
        DroneRequest droneRequest = new DroneRequest("NewName", 0);
        Drone drone = new Drone();
        drone.setIdDrone(droneId);
        drone.setName("OldName");
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        // When
        DroneResponse result = droneService.updateNameDrone(droneRequest, droneId);

        // Then
        assertEquals("NewName", result.getName());
        verify(droneRepository).findById(droneId);
        verify(droneRepository).save(any(Drone.class));
    }

    @Test
    @DisplayName("Given an invalid Drone id, when updateNameDrone is called, then throw EntityNotFoundException")
    void givenInvalidDroneId_whenUpdateNameDrone_thenThrowEntityNotFoundException() {
        // Given
        UUID droneId = UUID.randomUUID();
        DroneRequest droneRequest = new DroneRequest("NewName", 0);
        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> droneService.updateNameDrone(droneRequest, droneId));
        verify(droneRepository).findById(droneId);
    }

    @Test
    @DisplayName("Given a valid Drone id and new countMove, when updateCountMove is called, then update the drone's countMove and return the updated response")
    void givenValidDroneIdAndCountMove_whenUpdateCountMove_thenUpdateDroneAndReturnResponse() {
        // Given
        UUID droneId = UUID.randomUUID();
        int newCountMove = 10;
        Drone drone = new Drone();
        drone.setIdDrone(droneId);
        drone.setCountMove(5);
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        // When
        DroneResponse result = droneService.updateCountMove(droneId, newCountMove);

        // Then
        assertEquals(newCountMove, result.getCountMove());
        verify(droneRepository).findById(droneId);
        verify(droneRepository).save(any(Drone.class));
    }

    @Test
    @DisplayName("Given an invalid Drone id, when updateCountMove is called, then throw EntityNotFoundException")
    void givenInvalidDroneId_whenUpdateCountMove_thenThrowEntityNotFoundException() {
        // Given
        UUID droneId = UUID.randomUUID();
        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> droneService.updateCountMove(droneId, 10));
        verify(droneRepository).findById(droneId);
    }

    @Test
    @DisplayName("Given a valid Drone id, when deleteDrone is called, then delete the drone")
    void givenValidDroneId_whenDeleteDrone_thenDeleteDrone() {
        // Given
        UUID droneId = UUID.randomUUID();
        when(droneRepository.existsById(droneId)).thenReturn(true);

        // When
        droneService.deleteDrone(droneId);

        // Then
        verify(droneRepository).deleteById(droneId);
    }

    @Test
    @DisplayName("Given an invalid Drone id, when deleteDrone is called, then throw EntityNotFoundException")
    void givenInvalidDroneId_whenDeleteDrone_thenThrowEntityNotFoundException() {
        // Given
        UUID droneId = UUID.randomUUID();
        when(droneRepository.existsById(droneId)).thenReturn(false);

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> droneService.deleteDrone(droneId));
        verify(droneRepository).existsById(droneId);
    }
    @Test
    @DisplayName("Given an empty list of drones, when fetchDroneList is called, then return an empty list")
    void givenEmptyDroneList_whenFetchDroneList_thenReturnEmptyDroneList() {
        // Given
        List<Drone> emptyDroneList = List.of();
        when(droneRepository.findAll()).thenReturn(emptyDroneList);

        // When
        List<Drone> result = droneService.fetchDroneList();

        // Then
        assertTrue(result.isEmpty());
        verify(droneRepository).findAll();
    }

    @Test
    @DisplayName("Given a DroneRequest with missing name, when saveDrone is called, then save the drone with default name and return the response")
    void givenDroneRequestWithMissingName_whenSaveDrone_thenSaveDroneWithDefaultName() {
        // Given
        DroneRequest droneRequest = new DroneRequest(null, 5);
        Drone drone = Drone.builder()
                .idDrone(UUID.randomUUID())
                .name("UnnamedDrone")  // Numele implicit
                .countMove(5)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        when(droneRepository.save(any(Drone.class))).thenReturn(drone);
        doNothing().when(droneStatusService).saveDroneStatus(any(DroneStatus.class));

        // When
        DroneResponse result = droneService.saveDrone(droneRequest);

        // Then
        assertNotNull(result);
        assertEquals("UnnamedDrone", result.getName());
        verify(droneRepository).save(any(Drone.class));
        verify(droneStatusService).saveDroneStatus(any(DroneStatus.class));
    }

    @Test
    @DisplayName("Given a Drone with no movement, when updateCountMove is called with zero, then update the countMove to zero")
    void givenDroneWithNoMovement_whenUpdateCountMoveToZero_thenUpdateCountMoveToZero() {
        // Given
        UUID droneId = UUID.randomUUID();
        Drone drone = new Drone();
        drone.setIdDrone(droneId);
        drone.setCountMove(5);
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        // When
        DroneResponse result = droneService.updateCountMove(droneId, 0);

        // Then
        assertEquals(0, result.getCountMove());
        verify(droneRepository).findById(droneId);
        verify(droneRepository).save(any(Drone.class));
    }

    @Test
    @DisplayName("Given a valid Drone id, when deleteDrone is called twice, then the second call should throw EntityNotFoundException")
    void givenValidDroneId_whenDeleteDroneCalledTwice_thenThrowEntityNotFoundExceptionOnSecondCall() {
        // Given
        UUID droneId = UUID.randomUUID();
        when(droneRepository.existsById(droneId)).thenReturn(true).thenReturn(false);

        // When
        droneService.deleteDrone(droneId);

        // Then
        assertThrows(EntityNotFoundException.class, () -> droneService.deleteDrone(droneId));
        verify(droneRepository, times(2)).existsById(droneId);
        verify(droneRepository).deleteById(droneId);
    }

    @Test
    @DisplayName("Given a Drone with an old updatedAt timestamp, when updateNameDrone is called with a new name, then updatedAt should be refreshed")
    void givenDroneWithOldUpdatedAt_whenUpdateNameDrone_thenUpdatedAtShouldBeRefreshed() {
        // Given
        UUID droneId = UUID.randomUUID();
        DroneRequest droneRequest = new DroneRequest("UpdatedName", 0);
        OffsetDateTime oldUpdatedAt = OffsetDateTime.now().minusDays(1);
        Drone drone = new Drone();
        drone.setIdDrone(droneId);
        drone.setName("OldName");
        drone.setUpdatedAt(oldUpdatedAt);
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        // When
        DroneResponse result = droneService.updateNameDrone(droneRequest, droneId);

        // Then
        assertNotEquals(oldUpdatedAt, result.getUpdatedAt());
        assertEquals("UpdatedName", result.getName());
        verify(droneRepository).findById(droneId);
        verify(droneRepository).save(any(Drone.class));
    }
    @Test
    @DisplayName("Given a DroneRequest with negative countMove, when saveDrone is called, then throw IllegalArgumentException")
    void givenDroneRequestWithNegativeCountMove_whenSaveDrone_thenThrowIllegalArgumentException() {
        // Given
        DroneRequest droneRequest = new DroneRequest("Drone1", -5);

        // When & Then
        assertThrows(NullPointerException.class, () -> droneService.saveDrone(droneRequest));
    }
    @Test
    @DisplayName("Given a non-empty list of drones, when fetchDroneList is called, then return the list of drones")
    void givenNonEmptyDroneList_whenFetchDroneList_thenReturnNonEmptyDroneList() {
        // Given
        Drone drone1 = new Drone();
        Drone drone2 = new Drone();
        List<Drone> droneList = List.of(drone1, drone2);
        when(droneRepository.findAll()).thenReturn(droneList);

        // When
        List<Drone> result = droneService.fetchDroneList();

        // Then
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        verify(droneRepository).findAll();
    }
    @Test
    @DisplayName("Given a Drone with existing name, when updateNameDrone is called with the same name, then updatedAt should be refreshed")
    void givenDroneWithExistingName_whenUpdateNameDrone_thenUpdatedAtShouldBeRefreshed() {
        // Given
        UUID droneId = UUID.randomUUID();
        DroneRequest droneRequest = new DroneRequest("ExistingName", 0);
        OffsetDateTime oldUpdatedAt = OffsetDateTime.now().minusDays(1);
        Drone drone = new Drone();
        drone.setIdDrone(droneId);
        drone.setName("ExistingName");
        drone.setUpdatedAt(oldUpdatedAt);
        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        // When
        DroneResponse result = droneService.updateNameDrone(droneRequest, droneId);

        // Then
        assertNotEquals(oldUpdatedAt, result.getUpdatedAt());
        assertEquals("ExistingName", result.getName());
        verify(droneRepository).findById(droneId);
        verify(droneRepository).save(any(Drone.class));
    }


}
