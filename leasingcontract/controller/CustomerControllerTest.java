package com.allane.leasingcontract.controller;

import com.allane.leasingcontract.model.CustomerDTO;
import com.allane.leasingcontract.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    public void createCustomerTest() throws Exception {
        
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setBirthdate(new Date());

        
        Mockito.when(customerService.createCustomer(any(CustomerDTO.class)))
                .thenReturn(customerDTO);
        
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

       
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(201);
        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(customerDTO));

        
        Mockito.verify(customerService).createCustomer(eq(customerDTO));
    }

    @Test
    public void updateCustomerTest() throws Exception {
        
        int customerId = 1;
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setBirthdate(new Date());

        
        Mockito.when(customerService.updateCustomer(eq(customerId), any(CustomerDTO.class)))
                .thenReturn(customerDTO);

        
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

       
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(customerDTO));

        
        Mockito.verify(customerService).updateCustomer(eq(customerId), eq(customerDTO));
    }

    @Test
    public void getCustomerTest() throws Exception {
        
        int customerId = 1;
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setBirthdate(new Date());
        
        Mockito.when(customerService.getCustomer(eq(customerId)))
                .thenReturn(customerDTO);
        
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", customerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);
        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(customerDTO));
        
        Mockito.verify(customerService).getCustomer(eq(customerId));
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        
        int customerId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{id}", customerId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
        Mockito.verify(customerService).deleteCustomer(eq(customerId));
    }

}
