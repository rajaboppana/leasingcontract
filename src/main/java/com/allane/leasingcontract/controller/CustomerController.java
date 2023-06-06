package com.allane.leasingcontract.controller;

import com.allane.leasingcontract.model.CustomerDTO;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface CustomerController {

    ResponseEntity<CustomerDTO> createCustomer(CustomerDTO customerDTO);

    ResponseEntity<CustomerDTO> updateCustomer(int id, CustomerDTO customerDTO) throws ResourceNotFoundException;

    ResponseEntity<CustomerDTO> getCustomer(int id) throws ResourceNotFoundException;

    ResponseEntity<Void> deleteCustomer(int id) throws ResourceNotFoundException;
}