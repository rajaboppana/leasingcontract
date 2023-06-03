package com.allane.leasingcontract.repository;

import com.allane.leasingcontract.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Integer> {
}