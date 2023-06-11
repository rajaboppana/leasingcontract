package com.allane.leasingcontract.service;

import com.allane.leasingcontract.model.VehicleDTO;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;

public interface VehicleService {
    VehicleDTO createVehicle(VehicleDTO vehicleDTO);
    VehicleDTO getVehicleById(int id) throws ResourceNotFoundException;
    VehicleDTO updateVehicle(int id, VehicleDTO vehicleDTO) throws ResourceNotFoundException;
    boolean deleteVehicle(int id) throws ResourceNotFoundException;
}
