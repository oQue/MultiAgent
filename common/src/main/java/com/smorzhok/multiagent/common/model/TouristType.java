package com.smorzhok.multiagent.common.model;

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

    public static TouristType fromString(String name) {
        for (TouristType type : values) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No tourist type with name \"" + name + "\" found");
    }

    private String name;

    TouristType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
