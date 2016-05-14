package com.smorzhok.web.beans;

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

    private List<CountryStatisticsModel> list = new ArrayList<>();
    private String sessionId;
    private PortableRenderer renderer;

    public List<CountryStatisticsModel> getList() {
        return list;
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
        CountryStatisticsModel model = getByName(country);
        if (model == null) {
            model = new CountryStatisticsModel(country, price);
            list.add(model);
        } else {
            model.update(price);
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

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setRenderer(PortableRenderer renderer) {
        this.renderer = renderer;
    }
}
