package com.allane.leasingcontract.service;

import com.allane.leasingcontract.model.VehicleDTO;

public interface VehicleService {
    VehicleDTO createVehicle(VehicleDTO vehicleDTO);
    VehicleDTO getVehicleById(int id);
    VehicleDTO updateVehicle(int id, VehicleDTO vehicleDTO);
    boolean deleteVehicle(int id);
}
