package com.allane.leasingcontract.service.impl;

import com.allane.leasingcontract.entity.VehicleEntity;
import com.allane.leasingcontract.model.VehicleDTO;
import com.allane.leasingcontract.repository.VehicleRepository;
import com.allane.leasingcontract.service.VehicleService;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
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
    public VehicleDTO getVehicleById(int id) throws ResourceNotFoundException {
        Optional<VehicleEntity> vehicleOptional = vehicleRepository.findById(id);
        if(vehicleOptional.isEmpty())
            throw new ResourceNotFoundException("There are no vehicles available at " +
                    "this moment");
        return vehicleOptional.map(this::convertToDTO).orElse(null);
    }

    @Override
    public VehicleDTO updateVehicle(int id, VehicleDTO vehicleDTO) throws ResourceNotFoundException {
        Optional<VehicleEntity> vehicleOptional = vehicleRepository.findById(id);
        if (vehicleOptional.isPresent()) {
            VehicleEntity vehicleEntity = ContractTransformer.convertToEntity(vehicleDTO);
            VehicleEntity updatedVehicle = vehicleRepository.save(vehicleEntity);
            return ContractTransformer.convertToDTO(updatedVehicle);
        } else {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
    }

    @Override
    public boolean deleteVehicle(int id) throws ResourceNotFoundException {
        Optional<VehicleEntity> vehicleOptional = vehicleRepository.findById(id);
        if (vehicleOptional.isPresent()) {
            vehicleRepository.delete(vehicleOptional.get());
            return true;
        } else {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
    }

    private VehicleDTO convertToDTO(VehicleEntity vehicleEntity) {
        return ContractTransformer.convertToDTO(vehicleEntity);
    }

}
