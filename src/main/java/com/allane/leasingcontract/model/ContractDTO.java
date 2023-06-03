package com.allane.leasingcontract.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private BigDecimal monthlyRate;
    private CustomerDTO customer;
    private VehicleDTO vehicle;
}
