package com.allane.leasingcontract.service;

import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;

import java.util.List;

public interface ContractService {
    List<ContractDTO> getContracts();
    ContractDTO createContract(ContractDTO contractDTO);
    ContractDTO updateContract(int id, ContractDTO contractDTO) throws ResourceNotFoundException;
    ContractDTO getContract(int id) throws ResourceNotFoundException;
    void deleteContract(int id) throws ResourceNotFoundException;
}
