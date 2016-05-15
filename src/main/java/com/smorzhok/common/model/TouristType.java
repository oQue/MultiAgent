package com.smorzhok.common.model;

/**
 * Possible types of tourist agents which affects their destination preferences
 *
 * @author Dmitry Smorzhok
 */
public enum TouristType {

    /**
     * looking for cheapest tours
     */
    BUDGET("budget"),
    /**
     * selects tours by price/quality
     */
    REGULAR("regular"),
    /**
     * prefers the most luxury tours
     */
    LUXURY("luxury");

    public static final TouristType values[] = values();

    private String name;

    TouristType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
