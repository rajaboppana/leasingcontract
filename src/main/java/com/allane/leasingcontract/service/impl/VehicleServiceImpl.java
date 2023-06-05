package com.allane.leasingcontract.service.impl;

import com.allane.leasingcontract.entity.VehicleEntity;
import com.allane.leasingcontract.model.VehicleDTO;
import com.allane.leasingcontract.repository.VehicleRepository;
import com.allane.leasingcontract.service.VehicleService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        VehicleEntity vehicleEntity = ContractTransformer.convertToEntity(vehicleDTO);
        VehicleEntity savedVehicle = vehicleRepository.save(vehicleEntity);
        return ContractTransformer.convertToDTO(savedVehicle);
    }

    @Override
    public VehicleDTO getVehicleById(int id) {
        Optional<VehicleEntity> vehicleOptional = vehicleRepository.findById(id);
        return vehicleOptional.map(this::convertToDTO).orElse(null);
    }

    @Override
    public VehicleDTO updateVehicle(int id, VehicleDTO vehicleDTO) {
        Optional<VehicleEntity> vehicleOptional = vehicleRepository.findById(id);
        if (vehicleOptional.isPresent()) {
            VehicleEntity vehicleEntity = ContractTransformer.convertToEntity(vehicleDTO);
            VehicleEntity updatedVehicle = vehicleRepository.save(vehicleEntity);
            return ContractTransformer.convertToDTO(updatedVehicle);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteVehicle(int id) {
        Optional<VehicleEntity> vehicleOptional = vehicleRepository.findById(id);
        if (vehicleOptional.isPresent()) {
            vehicleRepository.delete(vehicleOptional.get());
            return true;
        } else {
            return false;
        }
    }

    private VehicleDTO convertToDTO(VehicleEntity vehicleEntity) {
        return ContractTransformer.convertToDTO(vehicleEntity);
    }

}
