package com.smorzhok.multiagent.web.model;

import com.smorzhok.multiagent.common.util.CountryAbbreviationUtil;

/**
 * @author Dmitry Smorzhok
 */
public class CountryStatisticsModel {

    private final String country;

    private final String isoCode;

    private double totalSum;

    private double averagePrice;

    private int toursBought;

    private int fillKey = 0;

    public CountryStatisticsModel(String country, double price) {
        this.country = country;
        this.isoCode = CountryAbbreviationUtil.fromName(country);
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

    public String getIsoCode() {
        return isoCode;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public String getAveragePriceAsString() {
        return String.format("%.2f", averagePrice);
    }

    public int getToursBought() {
        return toursBought;
    }

    public int getFillKey() {
        return fillKey;
    }

    public void setFillKey(int fillKey) {
        this.fillKey = fillKey;
    }
}
