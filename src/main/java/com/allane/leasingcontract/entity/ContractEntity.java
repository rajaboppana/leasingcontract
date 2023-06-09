package com.allane.leasingcontract.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "contract")
public class ContractEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "monthly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyRate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(BigDecimal monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public VehicleEntity getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleEntity vehicle) {
        this.vehicle = vehicle;
    }
}
