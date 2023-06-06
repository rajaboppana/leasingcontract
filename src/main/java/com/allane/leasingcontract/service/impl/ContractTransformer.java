package com.allane.leasingcontract.service.impl;

import com.allane.leasingcontract.entity.ContractEntity;
import com.allane.leasingcontract.entity.CustomerEntity;
import com.allane.leasingcontract.entity.VehicleEntity;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.model.CustomerDTO;
import com.allane.leasingcontract.model.VehicleDTO;

public class ContractTransformer {

    public  static ContractDTO convertToDTO(ContractEntity contractEntity) {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setMonthlyRate(contractEntity.getMonthlyRate());
        contractDTO.setCustomer(convertToDTO(contractEntity.getCustomer()));
        contractDTO.setVehicle(convertToDTO(contractEntity.getVehicle()));
        return contractDTO;
    }

    public static CustomerDTO convertToDTO(CustomerEntity customerEntity) {
        if (customerEntity != null) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customerEntity.getId());
            customerDTO.setFirstName(customerEntity.getFirstName());
            customerDTO.setLastName(customerEntity.getLastName());
            customerDTO.setBirthdate(customerEntity.getBirthdate());
            return customerDTO;
        }
        return null;
    }

    public static VehicleDTO convertToDTO(VehicleEntity vehicleEntity) {
        if (vehicleEntity != null) {
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setId(vehicleEntity.getId());
            vehicleDTO.setBrand(vehicleEntity.getBrand());
            vehicleDTO.setModel(vehicleEntity.getModel());
            vehicleDTO.setModelYear(vehicleEntity.getModelYear());
            vehicleDTO.setVin(vehicleEntity.getVin());
            vehicleDTO.setPrice(vehicleEntity.getPrice());
            return vehicleDTO;
        }
        return null;
    }

    public static ContractEntity convertToEntity(ContractDTO contractDTO) {
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setMonthlyRate(contractDTO.getMonthlyRate());
        contractEntity.setCustomer(convertToEntity(contractDTO.getCustomer()));
        contractEntity.setVehicle(convertToEntity(contractDTO.getVehicle()));
        return contractEntity;
    }

    public static CustomerEntity convertToEntity(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerDTO.getId());
        customerEntity.setFirstName(customerDTO.getFirstName());
        customerEntity.setLastName(customerDTO.getLastName());
        customerEntity.setBirthdate(customerDTO.getBirthdate());
        return customerEntity;
    }

    public static VehicleEntity convertToEntity(VehicleDTO vehicleDTO) {
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setId(vehicleDTO.getId());
        vehicleEntity.setBrand(vehicleDTO.getBrand());
        vehicleEntity.setModel(vehicleDTO.getModel());
        vehicleEntity.setModelYear(vehicleDTO.getModelYear());
        vehicleEntity.setVin(vehicleDTO.getVin());
        vehicleEntity.setPrice(vehicleDTO.getPrice());
        return vehicleEntity;
    }

    public static void updateEntityFromDTO(ContractEntity contractEntity, ContractDTO contractDTO) {
        contractEntity.setMonthlyRate(contractDTO.getMonthlyRate());
        updateEntityFromDTO(contractEntity.getCustomer(), contractDTO.getCustomer());
        updateEntityFromDTO(contractEntity.getVehicle(), contractDTO.getVehicle());
    }

    public static void updateEntityFromDTO(CustomerEntity customerEntity, CustomerDTO customerDTO) {
        if (customerEntity != null && customerDTO != null) {
            customerEntity.setFirstName(customerDTO.getFirstName());
            customerEntity.setLastName(customerDTO.getLastName());
            customerEntity.setBirthdate(customerDTO.getBirthdate());
        }
    }

    public static void updateEntityFromDTO(VehicleEntity vehicleEntity, VehicleDTO vehicleDTO) {
        if (vehicleEntity != null && vehicleDTO != null) {
            vehicleEntity.setBrand(vehicleDTO.getBrand());
            vehicleEntity.setModel(vehicleDTO.getModel());
            vehicleEntity.setModelYear(vehicleDTO.getModelYear());
            vehicleEntity.setVin(vehicleDTO.getVin());
            vehicleEntity.setPrice(vehicleDTO.getPrice());
        }
    }
}
