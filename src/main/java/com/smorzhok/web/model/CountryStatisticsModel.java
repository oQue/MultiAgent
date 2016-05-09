package com.smorzhok.web.model;

/**
 * @author Dmitry Smorzhok
 */
public class CountryStatisticsModel {

    private double totalSum;

    private double averagePrice;

    private int toursBought;

    public CountryStatisticsModel(double price) {
        totalSum = price;
        averagePrice = price;
        toursBought = 1;
    }

    public void update(double price) {
        totalSum += price;
        toursBought += 1;
        averagePrice = totalSum / toursBought;
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
