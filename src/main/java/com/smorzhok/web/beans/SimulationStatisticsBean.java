package com.smorzhok.web.beans;

import com.smorzhok.common.ModelCallback;
import com.smorzhok.common.StatisticsMessageContent;
import com.smorzhok.web.helper.Helper;
import com.smorzhok.web.model.CountryStatisticsModel;

import org.icefaces.application.PortableRenderer;
import org.icefaces.application.PushRenderer;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Dmitry Smorzhok
 */
@ManagedBean(name = "statisticsBean")
@SessionScoped
public class SimulationStatisticsBean implements ModelCallback, Serializable {

    private Map<String, CountryStatisticsModel> map = new TreeMap<>();
    private String sessionId;
    private PortableRenderer renderer;

    public Map<String, CountryStatisticsModel> getMap() {
        return map;
    }

    public void update(StatisticsMessageContent content) {
        if (sessionId == null) {
            throw new NullPointerException("SessionId should be set before updating");
        }
        if (renderer == null) {
            throw new NullPointerException("Renderer should be set before updating");
        }
        String country = content.getCountry();
        Double price = content.getPrice();
        CountryStatisticsModel model = map.get(country);
        if (model == null) {
            model = new CountryStatisticsModel(price);
            map.put(country, model);
        } else {
            model.update(price);
        }
        renderer.render(sessionId);
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setRenderer(PortableRenderer renderer) {
        this.renderer = renderer;
    }
}
