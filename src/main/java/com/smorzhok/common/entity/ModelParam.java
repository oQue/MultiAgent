package com.smorzhok.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Model Parameters Holder
 *
 * @author Dmitry Smorzhok
 */
@Entity
public class ModelParam implements DataObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int touristAmount;

    private int operatorAmount;

    private double averageSalary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTouristAmount() {
        return touristAmount;
    }

    public void setTouristAmount(int touristAmount) {
        this.touristAmount = touristAmount;
    }

    public int getOperatorAmount() {
        return operatorAmount;
    }

    public void setOperatorAmount(int operatorAmount) {
        this.operatorAmount = operatorAmount;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
    }
}
