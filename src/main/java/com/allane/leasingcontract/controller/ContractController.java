package com.allane.leasingcontract.controller;

import com.allane.leasingcontract.controller.transfer.ContractsTransfer;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.service.ContractService;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public ResponseEntity<ContractsTransfer> getContracts() throws ResourceNotFoundException {
        List<ContractDTO> contracts = contractService.getContracts();
        if (contracts == null || contracts.isEmpty()) {
            return ResponseEntity.ok(new ContractsTransfer(new ArrayList<ContractDTO>()));
        }
        return ResponseEntity.ok(new ContractsTransfer(contracts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable int id) throws ResourceNotFoundException {
        ContractDTO contract = contractService.getContract(id);
        return ResponseEntity.ok(contract);
    }

    @PostMapping
    public ResponseEntity<ContractDTO> createContract(@RequestBody ContractDTO contractDTO) {
        ContractDTO createdContract = contractService.createContract(contractDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContract);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractDTO> updateContract(@PathVariable int id, @RequestBody ContractDTO contractDTO)
            throws ResourceNotFoundException {
        ContractDTO updatedContract = contractService.updateContract(id, contractDTO);
        return ResponseEntity.ok(updatedContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable int id) throws ResourceNotFoundException {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}