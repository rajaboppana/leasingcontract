package com.allane.leasingcontract.controller;

import com.allane.leasingcontract.controller.transfer.ContractsTransfer;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.model.CustomerDTO;
import com.allane.leasingcontract.model.VehicleDTO;
import com.allane.leasingcontract.service.ContractService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ContractService contractService;

    @Test
    public void testGetContracts() {
        // Arrange
        List<ContractDTO> contracts = new ArrayList<>();
        contracts.add(new ContractDTO(1, new BigDecimal("1000.00"), new CustomerDTO(), new VehicleDTO()));
        contracts.add(new ContractDTO(2, new BigDecimal("1500.00"), new CustomerDTO(), new VehicleDTO()));
        when(contractService.getContracts()).thenReturn(contracts);

        // Act
        ResponseEntity<ContractsTransfer> response = restTemplate.getForEntity("/contracts", ContractsTransfer.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(contracts, response.getBody().getContractDTOList());
    }
}







