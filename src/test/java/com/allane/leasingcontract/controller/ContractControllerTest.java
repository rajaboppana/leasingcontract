package com.allane.leasingcontract.controller;

import com.allane.leasingcontract.controller.impl.ContractControllerImpl;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.service.ContractService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContractControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContractService contractService;

    @InjectMocks
    private ContractControllerImpl contractController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contractController).build();
    }

    @Test
    public void testGetContracts() throws Exception {
        
        List<ContractDTO> contracts = new ArrayList<>();
        contracts.add(new ContractDTO(new BigDecimal("1000"), null, null));

        
        when(contractService.getContracts()).thenReturn(contracts);

        // Perform GET request
        mockMvc.perform(get("/contracts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contracts[0].monthlyRate").value(1000));

        
        verify(contractService, times(1)).getContracts();
    }

    @Test
    public void testGetContractById() throws Exception {
        
        ContractDTO contract = new ContractDTO(new BigDecimal("1000"), null, null);

        
        when(contractService.getContract(anyInt())).thenReturn(contract);

        // Perform GET request
        mockMvc.perform(get("/contracts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyRate").value(1000));

        
        verify(contractService, times(1)).getContract(1);
    }

    @Test
    public void testCreateContract() throws Exception {
        
        ContractDTO contractDTO = new ContractDTO(new BigDecimal("1000"), null, null);

        
        when(contractService.createContract(any(ContractDTO.class))).thenReturn(contractDTO);

        // Perform POST request
        mockMvc.perform(post("/contracts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contractDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.monthlyRate").value(1000));

         
        verify(contractService, times(1)).createContract(any(ContractDTO.class));
    }

    @Test
    public void testUpdateContract() throws Exception {
        
        ContractDTO contractDTO = new ContractDTO(new BigDecimal("2000"), null, null);

        
        when(contractService.updateContract(anyInt(), any(ContractDTO.class))).thenReturn(contractDTO);

        // Perform PUT request
        mockMvc.perform(put("/contracts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contractDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyRate").value(2000));

        
        verify(contractService, times(1)).updateContract(anyInt(), any(ContractDTO.class));
    }

    @Test
    public void testDeleteContract() throws Exception {
        // Perform DELETE request
        mockMvc.perform(delete("/contracts/1"))
                .andExpect(status().isNoContent());

         
        verify(contractService, times(1)).deleteContract(1);
    }

    // Helper method to convert an object to JSON string
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
