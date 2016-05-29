package com.smorzhok.web.beans;

import com.google.gson.Gson;

import com.smorzhok.common.model.ModelCallback;
import com.smorzhok.common.StatisticsMessageContent;
import com.smorzhok.web.model.CountryStatisticsModel;

import org.icefaces.application.PortableRenderer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Dmitry Smorzhok
 */
@ManagedBean(name = "statisticsBean")
@SessionScoped
public class SimulationStatisticsBean implements ModelCallback, Serializable {

    private final List<CountryStatisticsModel> list = new ArrayList<>();
    private String jsonData;
    private String sessionId;
    private PortableRenderer renderer;

    public List<CountryStatisticsModel> getList() {
        return list;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public void update(StatisticsMessageContent content) {
        if (sessionId == null) {
            throw new NullPointerException("SessionId should be set before updating");
        }
        if (renderer == null) {
            throw new NullPointerException("Renderer should be set before updating");
        }
        String country = content.getCountry();
        Double price = content.getPrice();
        int totalToursBought = content.getTotalToursBought();
        CountryStatisticsModel model = getByName(country);
        synchronized (list) {
            if (model == null) {
                model = new CountryStatisticsModel(country, price);
                list.add(model);
            } else {
                model.update(price);
            }
            for (CountryStatisticsModel countryModel : list) {
                setFillKey(countryModel, totalToursBought);
            }
            jsonData = new Gson().toJson(list);
        }
        renderer.render(sessionId);
    }

    public void reset() {
        list.clear();
        renderer.render(sessionId);
    }

    private CountryStatisticsModel getByName(String country) {
        CountryStatisticsModel result = null;
        if (country != null) {
            for (CountryStatisticsModel model : list) {
                if (country.equals(model.getCountry())) {
                    result = model;
                    break;
                }
            }
        }
        return result;
    }

    private void setFillKey(CountryStatisticsModel countryModel, int totalToursBought) {
        int key;
        double percentage = countryModel.getToursBought() / (double) totalToursBought;
        if (percentage < 0.02) {
            key = 1;
        } else if (percentage < 0.05) {
            key = 2;
        } else if (percentage < 0.07) {
            key = 3;
        } else if (percentage < 0.10) {
            key = 4;
        } else if (percentage < 0.12) {
            key = 5;
        } else if (percentage < 0.15) {
            key = 6;
        } else if (percentage < 0.17) {
            key = 7;
        } else if (percentage < 0.20) {
            key = 8;
        } else if (percentage < 0.22) {
            key = 9;
        } else if (percentage < 0.25) {
            key = 10;
        } else {
            key = 11;
        }
        countryModel.setFillKey(key);
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setRenderer(PortableRenderer renderer) {
        this.renderer = renderer;
    }
}
