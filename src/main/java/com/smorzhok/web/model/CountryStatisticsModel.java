package com.smorzhok.web.model;

/**
 * @author Dmitry Smorzhok
 */
public class CountryStatisticsModel {

    private final String country;

    private double totalSum;

    private double averagePrice;

    private int toursBought;

    public CountryStatisticsModel(String country, double price) {
        this.country = country;
        totalSum = price;
        averagePrice = price;
        toursBought = 1;
    }

    public synchronized void update(double price) {
        totalSum += price;
        toursBought += 1;
        averagePrice = totalSum / toursBought;
    }

    public String getCountry() {
        return country;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public int getToursBought() {
        return toursBought;
    }
}
