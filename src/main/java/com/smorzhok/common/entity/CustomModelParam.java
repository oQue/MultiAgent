package com.smorzhok.common.entity;

import com.smorzhok.common.ModelParams;

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
public class CustomModelParam implements DataObject, ModelParams {

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

    @Override
    public int getTouristAmount() {
        return touristAmount;
    }

    public void setTouristAmount(int touristAmount) {
        this.touristAmount = touristAmount;
    }

    @Override
    public int getOperatorAmount() {
        return operatorAmount;
    }

    public void setOperatorAmount(int operatorAmount) {
        this.operatorAmount = operatorAmount;
    }

    @Override
    public double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
    }
}
