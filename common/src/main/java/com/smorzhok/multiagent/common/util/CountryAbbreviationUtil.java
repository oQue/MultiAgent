package com.smorzhok.multiagent.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for translations Country Name -> Country ISO
 *
 * @author Dmitry Smorzhok
 */
public final class CountryAbbreviationUtil {

    private static final Map<String, String> map = new HashMap<>();

    static {
        map.put("Albania", "ALB");
        map.put("Andorra", "AND");
        map.put("Austria", "AUT");
        map.put("Belarus", "BLR");
        map.put("Belgium", "BEL");
        map.put("Bosnia and Herzegovina", "BIH");
        map.put("Bulgaria", "BGR");
        map.put("Croatia", "HRV");
        map.put("Cyprus", "CYP");
        map.put("Czech Republic", "CZE");
        map.put("Denmark", "DNK");
        map.put("Estonia", "EST");
        map.put("Finland", "FIN");
        map.put("France", "FRA");
        map.put("Germany", "DEU");
        map.put("Greece", "GRC");
        map.put("Hungary", "HUN");
        map.put("Iceland", "ISL");
        map.put("Italy", "ITA");
        map.put("Latvia", "LVA");
        map.put("Lithuania", "LTU");
        map.put("Luxembourg", "LUX");
        map.put("Malta", "MLT");
        map.put("Monaco", "MCO");
        map.put("Montenegro", "MNE");
        map.put("Netherlands", "NLD");
        map.put("Norway", "NOR");
        map.put("Poland", "POL");
        map.put("Portugal", "PRT");
        map.put("Republic of Ireland", "IRL");
        map.put("Republic of Macedonia", "MKD");
        map.put("Serbia", "SRB");
        map.put("Spain", "ESP");
        map.put("Sweden", "SWE");
        map.put("Switzerland", "CHE");
        map.put("United Kingdom", "GBR");
    }

    private CountryAbbreviationUtil() {}

    /**
     * Returns country ISO-code by name
     * @param name  country name
     * @return      country ISO
     */
    public static String fromName(String name) {
        return map.get(name);
    }

}
