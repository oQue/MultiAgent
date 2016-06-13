package com.smorzhok.multiagent.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Country model params
 *
 * @author Dmitry Smorzhok
 */
@Entity
public class CountryParam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double averagePricePerDay;

    private double attractivenessCoefficient;

    @ManyToOne
    private Country country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAveragePricePerDay() {
        return averagePricePerDay;
    }

    public void setAveragePricePerDay(double averagePricePerDay) {
        this.averagePricePerDay = averagePricePerDay;
    }

    public double getAttractivenessCoefficient() {
        return attractivenessCoefficient;
    }

    public void setAttractivenessCoefficient(double attractivenessCoefficient) {
        this.attractivenessCoefficient = attractivenessCoefficient;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
