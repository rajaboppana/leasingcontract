package com.allane.leasingcontract.controller;

import com.allane.leasingcontract.controller.transfer.ContractsTransfer;
import com.allane.leasingcontract.model.ContractDTO;
import com.allane.leasingcontract.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public ResponseEntity<ContractsTransfer> getContractById() {
        List<ContractDTO> contracts = contractService.getContracts();
        if (contracts == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ContractsTransfer(contracts));
    }
}