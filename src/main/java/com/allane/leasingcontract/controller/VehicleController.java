package com.allane.leasingcontract.controller;

import com.allane.leasingcontract.model.VehicleDTO;
import org.springframework.http.ResponseEntity;

public interface VehicleController {

    ResponseEntity<VehicleDTO> createVehicle(VehicleDTO vehicleDTO);

    ResponseEntity<VehicleDTO> getVehicleById(int id);

    ResponseEntity<VehicleDTO> updateVehicle(int id, VehicleDTO vehicleDTO);

    ResponseEntity<Void> deleteVehicle(int id);
}