package com.allane.leasingcontract.controller.transfer;

import com.allane.leasingcontract.model.ContractDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractsTransfer {

    private List<ContractDTO> contracts;
}
