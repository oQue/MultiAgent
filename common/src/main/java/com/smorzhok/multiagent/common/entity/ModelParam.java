package com.smorzhok.multiagent.common.entity;

import org.springframework.util.CollectionUtils;

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

    private double salaryDeviation;

    private double minimumSalary;

    private double eurCurrencyRate;

    @OneToMany
    private Set<CountryParam> countryParams = new HashSet<>();

    @Transient
    private volatile Set<String> countries;

    @Transient
    private final Object countryMonitor = new Object();

    @Transient
    private volatile double averagePricePerDay = -1;

    @Transient
    private final Object priceMonitor = new Object();

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
        if (averageSalary < 0) {
            throw new IllegalArgumentException("Average salary can't be below zero");
        }
        this.averageSalary = averageSalary;
    }

    public double getSalaryDeviation() {
        return salaryDeviation;
    }

    public void setSalaryDeviation(double salaryDeviation) {
        if (salaryDeviation < 0) {
            throw new IllegalArgumentException("Salary deviation can't be below zero");
        }
        this.salaryDeviation = salaryDeviation;
    }

    public double getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(double minimumSalary) {
        if (minimumSalary < 0) {
            throw new IllegalArgumentException("Minimum salary can't be below zero");
        }
        this.minimumSalary = minimumSalary;
    }

    public double getEurCurrencyRate() {
        if (eurCurrencyRate < 0) {
            throw new IllegalArgumentException("Minimum salary can't be below zero");
        }
        return eurCurrencyRate;
    }

    public void setEurCurrencyRate(double eurCurrencyRate) {
        this.eurCurrencyRate = eurCurrencyRate;
    }

    public Set<CountryParam> getCountryParams() {
        return countryParams;
    }

    public void setCountryParams(Set<CountryParam> countryParams) {
        this.countryParams = countryParams;
    }

    public Set<String> getCountries() {
        Set<String> countryList = countries;
        if (CollectionUtils.isEmpty(countryList) && !countryParams.isEmpty()) {
            synchronized (countryMonitor) {
                countryList = countries;
                if (CollectionUtils.isEmpty(countryList)) {
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

    public double getAveragePricePerDay() {
        double price = averagePricePerDay;
        if (price == -1 && !countryParams.isEmpty()) {
            synchronized (priceMonitor) {
                price = averagePricePerDay;
                if (price == -1) {
                    for (CountryParam param : countryParams) {
                        if (price == -1) {
                            price = param.getAveragePricePerDay();
                        }
                        price += param.getAveragePricePerDay();
                    }
                }
                price /= countryParams.size();
                averagePricePerDay = price;
            }
        }
        if (price == -1) {
            price = 0;
            averagePricePerDay = price;
        }
        return price;
    }
}
