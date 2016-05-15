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

    private int duration;

    public StatisticsMessageContent(double price, String country, int duration) {
        this.price = price;
        this.country = country;
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public String getCountry() {
        return country;
    }

    public int getDuration() {
        return duration;
    }
}
