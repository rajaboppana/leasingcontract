package com.allane.leasingcontract.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDTO {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
}
