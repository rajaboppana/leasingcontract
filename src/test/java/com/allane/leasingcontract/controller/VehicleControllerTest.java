package com.allane.leasingcontract.controller;

import com.allane.leasingcontract.model.VehicleDTO;
import com.allane.leasingcontract.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class VehicleControllerTest {
    private MockMvc mockMvc;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
    }

    @Test
    public void testCreateVehicle() throws Exception {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand("Toyota");
        vehicleDTO.setModel("Corolla");
        vehicleDTO.setModelYear(2023);
        vehicleDTO.setVin("ABC123");
        vehicleDTO.setPrice(new BigDecimal("20000.00"));

        when(vehicleService.createVehicle(any(VehicleDTO.class))).thenReturn(vehicleDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"brand\": \"Toyota\", \"model\": \"Corolla\", \"modelYear\": 2023, \"vin\": \"ABC123\", \"price\": 20000.00 }"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Corolla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelYear").value(2023))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin").value("ABC123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("20000.0"));
    }

    @Test
    public void testGetVehicleById() throws Exception {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand("Toyota");
        vehicleDTO.setModel("Corolla");
        vehicleDTO.setModelYear(2023);
        vehicleDTO.setVin("ABC123");
        vehicleDTO.setPrice(new BigDecimal("20000.00"));

        when(vehicleService.getVehicleById(1)).thenReturn(vehicleDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Corolla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelYear").value(2023))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin").value("ABC123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("20000.0"));
    }

    @Test
    public void testGetVehicleById_NotFound() throws Exception {
        when(vehicleService.getVehicleById(1)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateVehicle() throws Exception {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand("Toyota");
        vehicleDTO.setModel("Corolla");
        vehicleDTO.setModelYear(2023);
        vehicleDTO.setVin("ABC123");
        vehicleDTO.setPrice(new BigDecimal("20000.00"));

        when(vehicleService.updateVehicle(eq(1), any(VehicleDTO.class))).thenReturn(vehicleDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"brand\": \"Toyota\", \"model\": \"Corolla\", \"modelYear\": 2023, \"vin\": \"ABC123\", \"price\": 20000.00 }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Corolla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelYear").value(2023))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin").value("ABC123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("20000.0"));
    }

    @Test
    public void testUpdateVehicle_NotFound() throws Exception {
        when(vehicleService.updateVehicle(eq(1), any(VehicleDTO.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"brand\": \"Toyota\", \"model\": \"Corolla\", \"modelYear\": 2023, \"vin\": \"ABC123\", \"price\": 20000.00 }"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteVehicle() throws Exception {
        when(vehicleService.deleteVehicle(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicles/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteVehicle_NotFound() throws Exception {
        when(vehicleService.deleteVehicle(1)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicles/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
