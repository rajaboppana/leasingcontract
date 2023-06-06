package com.allane.leasingcontract.service;

import com.allane.leasingcontract.entity.ContractEntity;
import com.allane.leasingcontract.entity.CustomerEntity;
import com.allane.leasingcontract.entity.VehicleEntity;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.model.CustomerDTO;
import com.allane.leasingcontract.model.VehicleDTO;
import com.allane.leasingcontract.repository.ContractRepository;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
import com.allane.leasingcontract.service.impl.ContractServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContractServiceImplTest {

    @Mock
    private ContractRepository contractRepository;

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
        ContractDTO contractDTO = createSampleContractDTO();
        ContractEntity savedContractEntity = createSampleContract();
        when(contractRepository.save(any(ContractEntity.class))).thenReturn(savedContractEntity);

        // Act
        ContractDTO createdContractDTO = contractService.createContract(contractDTO);

        // Assert
        verify(contractRepository, times(1)).save(contractEntityCaptor.capture());
        ContractEntity capturedContractEntity = contractEntityCaptor.getValue();
        assertEquals(savedContractEntity.getMonthlyRate(), capturedContractEntity.getMonthlyRate());
        assertEquals(savedContractEntity.getCustomer().getBirthdate().toString(),
                capturedContractEntity.getCustomer().getBirthdate().toString());
        assertEquals(savedContractEntity.getCustomer().getFirstName(),
                capturedContractEntity.getCustomer().getFirstName());
        assertEquals(createdContractDTO.getMonthlyRate(), contractDTO.getMonthlyRate());
        assertEquals(createdContractDTO.getCustomer().getBirthdate().toString(),
                contractDTO.getCustomer().getBirthdate().toString());
        assertEquals(createdContractDTO.getCustomer().getFirstName(),
                contractDTO.getCustomer().getFirstName());
    }

    @Test
    void testUpdateContract() throws Exception {
        // Arrange
        int id = 1;
        ContractDTO contractDTO = new ContractDTO();
        ContractEntity existingContractEntity = new ContractEntity();
        when(contractRepository.findById(id)).thenReturn(Optional.of(existingContractEntity));
        when(contractRepository.save(any(ContractEntity.class))).thenReturn(existingContractEntity);

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

    private ContractDTO createSampleContractDTO() {
        ContractDTO contract = new ContractDTO();
        contract.setMonthlyRate(new BigDecimal("1000.00"));

        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setBirthdate(new Date());

        VehicleDTO vehicle = new VehicleDTO();
        vehicle.setBrand("Toyota");
        vehicle.setModel("Camry");
        vehicle.setModelYear(2022);
        vehicle.setPrice(new BigDecimal("25000.00"));

        contract.setCustomer(customer);
        contract.setVehicle(vehicle);

        return contract;
    }

    private ContractEntity createSampleContract() {

        ContractEntity contract = new ContractEntity();
        contract.setMonthlyRate(new BigDecimal("1000.00"));

        CustomerEntity customer = new CustomerEntity();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setBirthdate(new Date());

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setBrand("Toyota");
        vehicle.setModel("Camry");
        vehicle.setModelYear(2022);
        vehicle.setPrice(new BigDecimal("25000.00"));

        contract.setCustomer(customer);
        vehicle.setContract(contract);
        contract.setVehicle(vehicle);

        return contract;
    }
}