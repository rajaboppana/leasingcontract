package com.allane.leasingcontract.service.impl;

import com.allane.leasingcontract.entity.CustomerEntity;
import com.allane.leasingcontract.model.CustomerDTO;
import com.allane.leasingcontract.repository.CustomerRepository;
import com.allane.leasingcontract.service.CustomerService;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = modelMapper.map(customerDTO, CustomerEntity.class);
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    @Override
    public CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) throws ResourceNotFoundException {
        CustomerEntity existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        BeanUtils.copyProperties(customerDTO, existingCustomer, "id");
        CustomerEntity updatedCustomer = customerRepository.save(existingCustomer);

        return modelMapper.map(updatedCustomer, CustomerDTO.class);
    }
}
