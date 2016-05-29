package com.smorzhok.common.model;

import com.smorzhok.common.entity.Country;
import com.smorzhok.common.entity.CountryParam;
import com.smorzhok.common.entity.ModelParam;
import com.smorzhok.common.service.SelectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for model parameters instances
 *
 * @author Dmitry Smorzhok
 */
@Component
public final class ModelParamsFactory {

    @Autowired
    private SelectionService selectionService;

    private static volatile ModelParam DEFAULT_MODEL;

    private final Object monitor = new Object();

    /**
     * Returns custom model from database by given ID
     * @param id    model ID
     * @return      model from database
     */
    public ModelParam customModelParams(long id) {
        return selectionService.getObjectById(ModelParam.class, id);
    }

    /**
     * Creates model filled with default parameters
     * Average salary defaults to Moscow, Russia (end of 2015) according to <a href="http://rg.ru/">rg.ru</a>
     * Minimum salary defaults to Moscow, Russia (beginning of 2016) according to <a href="http://garant.ru/">garant.ru</a>
     * Attractiveness coefficient is calculated by <a href="http://data.worldbank.org/indicator/ST.INT.ARVL">tourist flow stats</a>
     * France as a flow leader is taken as 1.0. Others coefficients are calculated as (Country Flow) / (France flow)
     * Average Price statistics are taken from <a href="http://www.budgetyourtrip.com/">Budget your trip</a>
     * Currency rate is set for RUB / EUR rate set on May 15, 2016
     *
     * @return default model
     */
    public ModelParam defaultModelParams() {
        ModelParam params = DEFAULT_MODEL;
        if (params == null) {
            synchronized (monitor) {
                params = DEFAULT_MODEL;
                if (params == null) {
                    params = new ModelParam();

                    params.setTouristAmount(100);
                    params.setOperatorAmount(5);
                    params.setAverageSalary(59000.00);
                    params.setMinimumSalary(17300.00);
                    params.setSalaryDeviation(30000.00);
                    params.setEurCurrencyRate(73.73);

                    addCountry(params, "Albania", 47.46, 0.04);
                    addCountry(params, "Andorra", 20.00, 0.03);
                    addCountry(params, "Austria", 97.75, 0.08);
                    addCountry(params, "Belarus", 18.52, 0.002);
                    addCountry(params, "Belgium", 106.62, 0.09);
                    addCountry(params, "Bosnia and Herzegovina", 53.59, 0.006);
                    addCountry(params, "Bulgaria", 43.03, 0.09);
                    addCountry(params, "Croatia", 65.11, 0.14);
                    addCountry(params, "Cyprus", 77.00, 0.03);
                    addCountry(params, "Czech Republic", 70.25, 0.13);
                    addCountry(params, "Denmark", 114.81, 0.12);
                    addCountry(params, "Estonia", 51.91, 0.03);
                    addCountry(params, "Finland", 114.66, 0.05);
                    addCountry(params, "France", 148.71, 1.0);
                    addCountry(params, "Germany", 96.81, 0.39);
                    addCountry(params, "Greece", 123.21, 0.26);
                    addCountry(params, "Hungary", 91.86, 0.14);
                    addCountry(params, "Iceland", 210.39, 0.01);
                    addCountry(params, "Italy", 119.31, 0.58);
                    addCountry(params, "Latvia", 46.56, 0.02);
                    addCountry(params, "Lithuania", 66.97, 0.02);
                    addCountry(params, "Luxembourg", 147.79, 0.01);
                    addCountry(params, "Malta", 81.29, 0.02);
                    addCountry(params, "Monaco", 165.72, 0.004);
                    addCountry(params, "Montenegro", 60.11, 0.02);
                    addCountry(params, "Netherlands", 119.29, 0.17);
                    addCountry(params, "Norway", 100.88, 0.06);
                    addCountry(params, "Poland", 50.61, 0.19);
                    addCountry(params, "Portugal", 82.82, 0.11);
                    addCountry(params, "Republic of Ireland", 96.87, 0.11);
                    addCountry(params, "Republic of Macedonia", 72.15, 0.005);
                    addCountry(params, "Serbia", 24.32, 0.01);
                    addCountry(params, "Slovenia", 74.59, 0.03);
                    addCountry(params, "Spain", 90.35, 0.78);
                    addCountry(params, "Sweden", 122.24, 0.07);
                    addCountry(params, "Switzerland", 157.42, 0.11);
                    addCountry(params, "United Kingdom", 143.80, 0.39);

                    DEFAULT_MODEL = params;
                }
            }
        }
        return params;
    }

    private void addCountry(ModelParam params, String name, double avgPrice, double attractiveness) {
        CountryParam countryParam = new CountryParam();
        Country country = new Country();
        country.setName(name);
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(avgPrice);
        countryParam.setAttractivenessCoefficient(attractiveness);
        params.getCountryParams().add(countryParam);
    }

}
