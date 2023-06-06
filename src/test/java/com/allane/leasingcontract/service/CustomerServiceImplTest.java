package com.allane.leasingcontract.service;

import com.allane.leasingcontract.entity.CustomerEntity;
import com.allane.leasingcontract.model.CustomerDTO;
import com.allane.leasingcontract.repository.CustomerRepository;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
import com.allane.leasingcontract.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Captor
    private ArgumentCaptor<CustomerEntity> customerEntityCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        // Arrange
        CustomerDTO customerDTO = createCustomerDTO();
        CustomerEntity savedCustomerEntity = createCustomerEntity();
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedCustomerEntity);

        // Act
        CustomerDTO createdCustomerDTO = customerService.createCustomer(customerDTO);

        // Assert
        verify(customerRepository, times(1)).save(customerEntityCaptor.capture());
        CustomerEntity capturedCustomerEntity = customerEntityCaptor.getValue();
        assertEquals(savedCustomerEntity.getFirstName(), capturedCustomerEntity.getFirstName());
        assertEquals(savedCustomerEntity.getBirthdate(), capturedCustomerEntity.getBirthdate());
        assertEquals(createdCustomerDTO.getFirstName(), customerDTO.getFirstName());
        assertEquals(createdCustomerDTO.getBirthdate(), customerDTO.getBirthdate());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        // Arrange
        int id = 1;
        CustomerDTO customerDTO = new CustomerDTO();
        CustomerEntity existingCustomerEntity = new CustomerEntity();
        when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomerEntity));
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(existingCustomerEntity);

        // Act
        CustomerDTO updatedCustomerDTO = customerService.updateCustomer(id, customerDTO);

        // Assert
        verify(customerRepository, times(1)).save(customerEntityCaptor.capture());
        CustomerEntity capturedCustomerEntity = customerEntityCaptor.getValue();
        assertEquals(existingCustomerEntity, capturedCustomerEntity);
        assertEquals(updatedCustomerDTO, new CustomerDTO());
    }

    @Test
    void testUpdateCustomerNotFound() {
        // Arrange
        int id = 1;
        CustomerDTO customerDTO = new CustomerDTO();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(id, customerDTO));
    }

    @Test
    void testGetCustomer() throws Exception {
        // Arrange
        int id = 1;
        CustomerEntity customerEntity = new CustomerEntity();
        when(customerRepository.findById(id)).thenReturn(Optional.of(customerEntity));

        // Act
        CustomerDTO retrievedCustomerDTO = customerService.getCustomer(id);

        // Assert
        assertEquals(new CustomerDTO(), retrievedCustomerDTO);
    }

    @Test
    void testGetCustomerNotFound() {
        // Arrange
        int id = 1;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomer(id));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        // Arrange
        int id = 1;
        CustomerEntity customerEntity = new CustomerEntity();
        when(customerRepository.findById(id)).thenReturn(Optional.of(customerEntity));

        // Act
        customerService.deleteCustomer(id);

        // Assert
        verify(customerRepository, times(1)).delete(customerEntity);
    }

    @Test
    void testDeleteCustomerNotFound() {
        // Arrange
        int id = 1;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(id));
    }

    private CustomerEntity createCustomerEntity(){
        CustomerEntity customer = new CustomerEntity();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setBirthdate(new Date());
        return customer;
    }

    private CustomerDTO createCustomerDTO(){
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setBirthdate(new Date());
        return customer;
    }
}