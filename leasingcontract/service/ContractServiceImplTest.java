package com.allane.leasingcontract.service;

import com.allane.leasingcontract.entity.ContractEntity;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.repository.ContractRepository;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
import com.allane.leasingcontract.service.impl.ContractServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ContractServiceImplTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ContractServiceImpl contractService;

    @Captor
    private ArgumentCaptor<ContractEntity> contractEntityCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetContracts() {
        // Arrange
        List<ContractEntity> contractEntities = new ArrayList<>();
        contractEntities.add(new ContractEntity());
        contractEntities.add(new ContractEntity());
        when(contractRepository.findAll()).thenReturn(contractEntities);

        List<ContractDTO> expectedContractDTOList = new ArrayList<>();
        expectedContractDTOList.add(new ContractDTO());
        expectedContractDTOList.add(new ContractDTO());
        when(modelMapper.map(any(ContractEntity.class), eq(ContractDTO.class))).thenReturn(new ContractDTO());

        // Act
        List<ContractDTO> contractDTOList = contractService.getContracts();

        // Assert
        assertEquals(expectedContractDTOList.size(), contractDTOList.size());
        assertEquals(expectedContractDTOList.get(0), contractDTOList.get(0));
        assertEquals(expectedContractDTOList.get(1), contractDTOList.get(1));
    }

    @Test
    void testGetContractsNoContractsAvailable() {
        // Arrange
        when(contractRepository.findAll()).thenReturn(new ArrayList<>());

        // Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> contractService.getContracts());
        assertEquals("There are no contracts available at this moment", exception.getMessage());
    }

    @Test
    void testCreateContract() {
        // Arrange
        ContractDTO contractDTO = new ContractDTO();
        ContractEntity savedContractEntity = new ContractEntity();
        when(modelMapper.map(eq(contractDTO), eq(ContractEntity.class))).thenReturn(savedContractEntity);
        when(contractRepository.save(any(ContractEntity.class))).thenReturn(savedContractEntity);
        when(modelMapper.map(eq(savedContractEntity), eq(ContractDTO.class))).thenReturn(new ContractDTO());

        // Act
        ContractDTO createdContractDTO = contractService.createContract(contractDTO);

        // Assert
        verify(contractRepository, times(1)).save(contractEntityCaptor.capture());
        ContractEntity capturedContractEntity = contractEntityCaptor.getValue();
        assertEquals(savedContractEntity, capturedContractEntity);
        assertEquals(createdContractDTO, new ContractDTO());
    }

    @Test
    void testUpdateContract() throws Exception {
        // Arrange
        int id = 1;
        ContractDTO contractDTO = new ContractDTO();
        ContractEntity existingContractEntity = new ContractEntity();
        when(contractRepository.findById(id)).thenReturn(Optional.of(existingContractEntity));
        doNothing().when(modelMapper).map(eq(contractDTO), any(ContractEntity.class));
        when(contractRepository.save(any(ContractEntity.class))).thenReturn(existingContractEntity);
        when(modelMapper.map(eq(existingContractEntity), eq(ContractDTO.class))).thenReturn(new ContractDTO());

        // Act
        ContractDTO updatedContractDTO = contractService.updateContract(id, contractDTO);

        // Assert
        verify(contractRepository, times(1)).save(contractEntityCaptor.capture());
        ContractEntity capturedContractEntity = contractEntityCaptor.getValue();
        assertEquals(existingContractEntity, capturedContractEntity);
        assertEquals(updatedContractDTO, new ContractDTO());
    }

    @Test
    void testUpdateContractNotFound() {
        // Arrange
        int id = 1;
        ContractDTO contractDTO = new ContractDTO();
        when(contractRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> contractService.updateContract(id, contractDTO));
    }

    @Test
    void testGetContract() throws Exception{
        // Arrange
        int id = 1;
        ContractEntity contractEntity = new ContractEntity();
        when(contractRepository.findById(id)).thenReturn(Optional.of(contractEntity));
        when(modelMapper.map(eq(contractEntity), eq(ContractDTO.class))).thenReturn(new ContractDTO());

        // Act
        ContractDTO retrievedContractDTO = contractService.getContract(id);

        // Assert
        assertEquals(new ContractDTO(), retrievedContractDTO);
    }

    @Test
    void testGetContractNotFound() {
        // Arrange
        int id = 1;
        when(contractRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> contractService.getContract(id));
    }

    @Test
    void testDeleteContract() throws Exception {
        // Arrange
        int id = 1;
        ContractEntity contractEntity = new ContractEntity();
        when(contractRepository.findById(id)).thenReturn(Optional.of(contractEntity));

        // Act
        contractService.deleteContract(id);

        // Assert
        verify(contractRepository, times(1)).delete(contractEntity);
    }

    @Test
    void testDeleteContractNotFound() {
        // Arrange
        int id = 1;
        when(contractRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> contractService.deleteContract(id));
    }
}