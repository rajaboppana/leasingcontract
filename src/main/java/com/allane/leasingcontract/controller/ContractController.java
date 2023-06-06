package com.allane.leasingcontract.controller;

import com.allane.leasingcontract.controller.transfer.ContractsTransfer;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface ContractController {

    ResponseEntity<ContractsTransfer> getContracts();

    ResponseEntity<ContractDTO> getContractById(int id) throws ResourceNotFoundException;

    ResponseEntity<ContractDTO> createContract(ContractDTO contractDTO);

    ResponseEntity<ContractDTO> updateContract(int id, ContractDTO contractDTO) throws ResourceNotFoundException;

    ResponseEntity<Void> deleteContract(int id) throws ResourceNotFoundException;

}
