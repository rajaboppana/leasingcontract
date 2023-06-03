package com.allane.leasingcontract.repository;

import com.allane.leasingcontract.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, Integer> {
}