package com.smorzhok.web.beans;

import com.smorzhok.jade.JadeRunner;
import com.smorzhok.web.helper.Helper;

import org.icefaces.application.PortableRenderer;
import org.icefaces.application.PushRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import jade.util.leap.Serializable;
import jade.wrapper.StaleProxyException;

/**
 * @author Dmitry Smorzhok
 */
@ManagedBean(name = "agentBean")
@SessionScoped
public class AgentBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgentBean.class);

    @ManagedProperty(value = "#{statisticsBean}")
    private SimulationStatisticsBean statisticsBean;
    private String sessionId;

    public void setStatisticsBean(SimulationStatisticsBean statisticsBean) {
        this.statisticsBean = statisticsBean;
    }

    public AgentBean() {
        sessionId = Helper.getCurrentSession();
        PushRenderer.addCurrentSession(sessionId);
    }

    public void runSimulation() throws StaleProxyException {
        PortableRenderer renderer = PushRenderer.getPortableRenderer();
        statisticsBean.setRenderer(renderer);
        statisticsBean.setSessionId(sessionId);
        JadeRunner.run(statisticsBean);
    }

}
