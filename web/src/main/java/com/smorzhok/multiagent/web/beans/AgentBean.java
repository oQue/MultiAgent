package com.smorzhok.multiagent.web.beans;

import com.smorzhok.multiagent.common.ContextHolder;
import com.smorzhok.multiagent.common.model.ModelParamsFactory;
import com.smorzhok.multiagent.jade.JadeRunner;
import com.smorzhok.multiagent.web.helper.Helper;

import org.icefaces.application.PortableRenderer;
import org.icefaces.application.PushRenderer;
import org.icefaces.util.JavaScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import jade.util.leap.Serializable;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

/**
 * @author Dmitry Smorzhok
 */
@ManagedBean(name = "agentBean")
@SessionScoped
public class AgentBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgentBean.class);

    private ModelParamsFactory modelParamsFactory =
            (ModelParamsFactory) ContextHolder.instance().getBean("modelParamsFactory");

    @ManagedProperty(value = "#{statisticsBean}")
    private SimulationStatisticsBean statisticsBean;
    private String sessionId;
    private AgentContainer container;

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
        statisticsBean.reset();
        container = JadeRunner.run(modelParamsFactory.defaultModelParams(), statisticsBean);
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "startUpdating()");
    }

    public void stopSimulation() throws StaleProxyException {
        if (container != null) {
            container.kill();
            container = null;
        }
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "abortTimer()");
    }

}
