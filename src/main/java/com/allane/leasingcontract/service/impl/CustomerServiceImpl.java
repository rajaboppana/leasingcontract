package com.allane.leasingcontract.service.impl;

import com.allane.leasingcontract.entity.CustomerEntity;
import com.allane.leasingcontract.model.CustomerDTO;
import com.allane.leasingcontract.repository.CustomerRepository;
import com.allane.leasingcontract.service.CustomerService;
import com.allane.leasingcontract.service.exception.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = ContractTransformer.convertToEntity(customerDTO);
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        return ContractTransformer.convertToDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) throws ResourceNotFoundException {
        CustomerEntity existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        BeanUtils.copyProperties(customerDTO, existingCustomer, "id");
        CustomerEntity updatedCustomer = customerRepository.save(existingCustomer);

        return ContractTransformer.convertToDTO(updatedCustomer);
    }

    @Override
    public CustomerDTO getCustomer(int id) throws ResourceNotFoundException {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        return ContractTransformer.convertToDTO(customerEntity);
    }

    @Override
    public void deleteCustomer(int id) throws ResourceNotFoundException {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customerEntity);
    }
}
