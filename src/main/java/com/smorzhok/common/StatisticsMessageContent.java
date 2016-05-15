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

    private double popularity;

    public StatisticsMessageContent(double price, String country, int duration, double popularity) {
        this.price = price;
        this.country = country;
        this.duration = duration;
        this.popularity = popularity;
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

    public double getPopularity() {
        return popularity;
    }
}
