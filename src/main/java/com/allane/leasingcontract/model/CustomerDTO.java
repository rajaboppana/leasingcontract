package com.allane.leasingcontract.model;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerDTO {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthdate;
}
