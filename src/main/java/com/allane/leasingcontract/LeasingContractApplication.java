package com.allane.leasingcontract;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Contract API", version = "2.0", description =
        "Contract Information"))
public class LeasingContractApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeasingContractApplication.class, args);
    }

}
