package com.smorzhok.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * Model Parameters
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

    @OneToMany
    private Set<CountryParam> countryParams = new HashSet<>();

    @Transient
    private volatile Set<String> countries;

    @Transient
    private final Object monitor = new Object();

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

    public Set<CountryParam> getCountryParams() {
        return countryParams;
    }

    public void setCountryParams(Set<CountryParam> countryParams) {
        this.countryParams = countryParams;
    }

    public Set<String> getCountries() {
        Set<String> countryList = countries;
        if (countryList == null) {
            synchronized (monitor) {
                countryList = countries;
                if (countryList == null) {
                    countryList = new HashSet<>(countryParams.size());
                    for (CountryParam countryParam : countryParams) {
                        countryList.add(countryParam.getCountry().getName());
                    }
                    countries = countryList;
                }
            }
        }
        return countryList;
    }
}
