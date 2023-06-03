package com.allane.leasingcontract.service;

import com.allane.leasingcontract.model.CustomerDTO;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) throws ResourceNotFoundException;
}
