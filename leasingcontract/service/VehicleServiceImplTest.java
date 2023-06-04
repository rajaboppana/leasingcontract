package com.allane.leasingcontract.service;

import com.allane.leasingcontract.entity.VehicleEntity;
import com.allane.leasingcontract.model.VehicleDTO;
import com.allane.leasingcontract.repository.VehicleRepository;
import com.allane.leasingcontract.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Captor
    private ArgumentCaptor<VehicleEntity> vehicleEntityCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createVehicle_ShouldSaveVehicleEntity() {
        // Arrange
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand("Toyota");
        vehicleDTO.setModel("Camry");
        vehicleDTO.setModelYear(2022);
        vehicleDTO.setVin("ABC123");
        vehicleDTO.setPrice(BigDecimal.valueOf(25000));

        when(vehicleRepository.save(any(VehicleEntity.class))).thenReturn(new VehicleEntity());

        // Act
        VehicleDTO savedVehicle = vehicleService.createVehicle(vehicleDTO);

        // Assert
        verify(vehicleRepository).save(vehicleEntityCaptor.capture());
        VehicleEntity capturedVehicleEntity = vehicleEntityCaptor.getValue();
        assertEquals("Toyota", capturedVehicleEntity.getBrand());
        assertEquals("Camry", capturedVehicleEntity.getModel());
        assertEquals(2022, capturedVehicleEntity.getModelYear());
        assertEquals("ABC123", capturedVehicleEntity.getVin());
        assertEquals(25000, capturedVehicleEntity.getPrice());

        // Additional assertions on the savedVehicle if needed
    }

    @Test
    void getVehicleById_ExistingId_ShouldReturnVehicleDTO() {
        // Arrange
        int id = 1;
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setId(id);
        vehicleEntity.setBrand("Toyota");
        vehicleEntity.setModel("Camry");
        vehicleEntity.setModelYear(2022);
        vehicleEntity.setVin("ABC123");
        vehicleEntity.setPrice(25000);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicleEntity));

        // Act
        VehicleDTO foundVehicle = vehicleService.getVehicleById(id);

        // Assert
        assertNotNull(foundVehicle);
        assertEquals("Toyota", foundVehicle.getBrand());
        assertEquals("Camry", foundVehicle.getModel());
        assertEquals(2022, foundVehicle.getModelYear());
        assertEquals("ABC123", foundVehicle.getVin());
        assertEquals(BigDecimal.valueOf(25000), foundVehicle.getPrice());
    }

    @Test
    void getVehicleById_NonexistentId_ShouldReturnNull() {
        // Arrange
        int id = 1;

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        VehicleDTO foundVehicle = vehicleService.getVehicleById(id);

        // Assert
        assertNull(foundVehicle);
    }

    @Test
    void updateVehicle_ExistingId_ShouldUpdateAndReturnUpdatedVehicleDTO() {
        // Arrange
        int id = 1;
        VehicleDTO updatedVehicleDTO = new VehicleDTO();
        updatedVehicleDTO.setBrand("Toyota");
        updatedVehicleDTO.setModel("Corolla");
        updatedVehicleDTO.setModelYear(2023);
        updatedVehicleDTO.setVin("XYZ789");
        updatedVehicleDTO.setPrice(BigDecimal.valueOf(30000));

        VehicleEntity existingVehicleEntity = new VehicleEntity();
        existingVehicleEntity.setId(id);
        existingVehicleEntity.setBrand("Toyota");
        existingVehicleEntity.setModel("Camry");
        existingVehicleEntity.setModelYear(2022);
        existingVehicleEntity.setVin("ABC123");
        existingVehicleEntity.setPrice(25000);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(existingVehicleEntity));
        when(vehicleRepository.save(any(VehicleEntity.class))).thenReturn(existingVehicleEntity);

        // Act
        VehicleDTO updatedVehicle = vehicleService.updateVehicle(id, updatedVehicleDTO);

        // Assert
        assertNotNull(updatedVehicle);
        assertEquals("Toyota", updatedVehicle.getBrand());
        assertEquals("Corolla", updatedVehicle.getModel());
        assertEquals(2023, updatedVehicle.getModelYear());
        assertEquals("XYZ789", updatedVehicle.getVin());
        assertEquals(BigDecimal.valueOf(30000), updatedVehicle.getPrice());
    }

    @Test
    void updateVehicle_NonexistentId_ShouldReturnNull() {
        // Arrange
        int id = 1;
        VehicleDTO updatedVehicleDTO = new VehicleDTO();
        updatedVehicleDTO.setBrand("Toyota");
        updatedVehicleDTO.setModel("Corolla");
        updatedVehicleDTO.setModelYear(2023);
        updatedVehicleDTO.setVin("XYZ789");
        updatedVehicleDTO.setPrice(BigDecimal.valueOf(30000));

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        VehicleDTO updatedVehicle = vehicleService.updateVehicle(id, updatedVehicleDTO);

        // Assert
        assertNull(updatedVehicle);
    }

    @Test
    void deleteVehicle_ExistingId_ShouldDeleteAndReturnTrue() {
        // Arrange
        int id = 1;
        VehicleEntity existingVehicleEntity = new VehicleEntity();
        existingVehicleEntity.setId(id);
        existingVehicleEntity.setBrand("Toyota");
        existingVehicleEntity.setModel("Camry");
        existingVehicleEntity.setModelYear(2022);
        existingVehicleEntity.setVin("ABC123");
        existingVehicleEntity.setPrice(25000);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(existingVehicleEntity));

        // Act
        boolean isDeleted = vehicleService.deleteVehicle(id);

        // Assert
        assertTrue(isDeleted);
        verify(vehicleRepository).delete(existingVehicleEntity);
    }

    @Test
    void deleteVehicle_NonexistentId_ShouldReturnFalse() {
        // Arrange
        int id = 1;

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean isDeleted = vehicleService.deleteVehicle(id);

        // Assert
        assertFalse(isDeleted);
        verify(vehicleRepository, never()).delete(any());
    }

}
