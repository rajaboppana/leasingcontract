package com.allane.leasingcontract.service;

import com.allane.leasingcontract.entity.CustomerEntity;
import com.allane.leasingcontract.model.CustomerDTO;
import com.allane.leasingcontract.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SpringBootTest
@Testcontainers
public class ITCustomerTest {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse(
            "mysql:8.0-debian")).withInitScript("customer-data.sql");

    @DynamicPropertySource
    static void mySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> mySQLContainer.getJdbcUrl());
        registry.add("spring.datasource.driverClassName", () -> mySQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> mySQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> mySQLContainer.getPassword());
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQL8Dialect");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQL8Dialect");
    }

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;


    @Test
    void testCreateCustomer() throws Exception {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"));

        // Act
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);

        // Assert
        Assertions.assertEquals("John", createdCustomer.getFirstName());
        Assertions.assertEquals("Doe", createdCustomer.getLastName());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(createdCustomer.getBirthdate());
        Assertions.assertEquals("2000-01-01",strDate);
    }

    @Test
    void testUpdateCustomer() throws Exception {
        // Arrange
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName("Jane");
        customerEntity.setLastName("Doe");
        customerEntity.setBirthdate(new SimpleDateFormat("yyyy-MM-dd")
                .parse("1990-04-15"));

        CustomerEntity savedCustomer = customerRepository.save(customerEntity);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Janet");
        customerDTO.setLastName("Smith");
        customerDTO.setBirthdate(new SimpleDateFormat("yyyy-MM-dd")
                .parse("1990-05-20"));

        // Act
        CustomerDTO updatedCustomer = customerService.updateCustomer(savedCustomer.getId()
                , customerDTO);

        // Assert
        Assertions.assertEquals(savedCustomer.getId(), updatedCustomer.getId());
        Assertions.assertEquals("Janet", updatedCustomer.getFirstName());
        Assertions.assertEquals("Smith", updatedCustomer.getLastName());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(updatedCustomer.getBirthdate());
        Assertions.assertEquals("1990-05-20",
                strDate);
    }
}
