package com.smorzhok.common;

import java.io.Serializable;

/**
 * Message content for a statistics agent
 *
 * @author Dmitry Smorzhok
 */
public class StatisticsMessageContent implements Serializable {

    private double price;

    private String country;

    public StatisticsMessageContent(double price, String country) {
        this.price = price;
        this.country = country;
    }

    public double getPrice() {
        return price;
    }

    public String getCountry() {
        return country;
    }
}
