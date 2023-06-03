package com.allane.leasingcontract.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class VehicleDTO {
    private int id;
    private String brand;
    private String model;
    private int modelYear;
    private String vin;
    private BigDecimal price;
}
