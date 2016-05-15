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
     *
     * @return default model
     */
    public ModelParam defaultModelParams() {
        ModelParam params = new ModelParam();

        params.setTouristAmount(10);
        params.setOperatorAmount(1);
        params.setAverageSalary(59000.00);
        params.setMinimumSalary(17300.00);
        params.setSalaryDeviation(30000.00);

        CountryParam countryParam = new CountryParam();
        Country country = new Country();
        country.setName("Albania");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(47.46);
        countryParam.setAttractivenessCoefficient(0.04);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Andorra");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(20.00);
        countryParam.setAttractivenessCoefficient(0.03);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Austria");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(97.75);
        countryParam.setAttractivenessCoefficient(0.08);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Belarus");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(18.52);
        countryParam.setAttractivenessCoefficient(0.002);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Belgium");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(18.52);
        countryParam.setAttractivenessCoefficient(0.09);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Bosnia and Herzegovina");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(53.59);
        countryParam.setAttractivenessCoefficient(0.006);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Bulgaria");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(43.03);
        countryParam.setAttractivenessCoefficient(0.09);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Croatia");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(65.11);
        countryParam.setAttractivenessCoefficient(0.14);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Cyprus");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(77.00);
        countryParam.setAttractivenessCoefficient(0.03);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Czech Republic");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(70.25);
        countryParam.setAttractivenessCoefficient(0.13);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Denmark");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(114.81);
        countryParam.setAttractivenessCoefficient(0.12);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Estonia");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(51.91);
        countryParam.setAttractivenessCoefficient(0.03);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Finland");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(114.66);
        countryParam.setAttractivenessCoefficient(0.05);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Germany");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(96.81);
        countryParam.setAttractivenessCoefficient(0.39);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Greece");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(123.21);
        countryParam.setAttractivenessCoefficient(0.26);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Hungary");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(91.86);
        countryParam.setAttractivenessCoefficient(0.14);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Iceland");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(210.39);
        countryParam.setAttractivenessCoefficient(0.01);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Italy");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(119.31);
        countryParam.setAttractivenessCoefficient(0.58);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Latvia");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(46.56);
        countryParam.setAttractivenessCoefficient(0.02);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Lithuania");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(66.97);
        countryParam.setAttractivenessCoefficient(0.02);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Luxembourg");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(147.79);
        countryParam.setAttractivenessCoefficient(0.01);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Malta");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(81.29);
        countryParam.setAttractivenessCoefficient(0.02);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Monaco");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(165.72);
        countryParam.setAttractivenessCoefficient(0.004);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Montenegro");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(60.11);
        countryParam.setAttractivenessCoefficient(0.02);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Netherlands");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(119.29);
        countryParam.setAttractivenessCoefficient(0.17);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Norway");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(100.88);
        countryParam.setAttractivenessCoefficient(0.06);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Poland");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(50.61);
        countryParam.setAttractivenessCoefficient(0.19);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Portugal");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(82.82);
        countryParam.setAttractivenessCoefficient(0.11);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Republic of Ireland");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(96.87);
        countryParam.setAttractivenessCoefficient(0.11);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Republic of Macedonia");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(72.15);
        countryParam.setAttractivenessCoefficient(0.005);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Serbia");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(24.32);
        countryParam.setAttractivenessCoefficient(0.01);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Slovenia");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(74.59);
        countryParam.setAttractivenessCoefficient(0.03);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Spain");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(90.35);
        countryParam.setAttractivenessCoefficient(0.78);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Sweden");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(122.24);
        countryParam.setAttractivenessCoefficient(0.07);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("Switzerland");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(157.42);
        countryParam.setAttractivenessCoefficient(0.11);
        params.getCountryParams().add(countryParam);

        countryParam = new CountryParam();
        country = new Country();
        country.setName("United Kingdom");
        countryParam.setCountry(country);
        countryParam.setAveragePricePerDay(143.80);
        countryParam.setAttractivenessCoefficient(0.39);
        params.getCountryParams().add(countryParam);

        return params;
    }

}
