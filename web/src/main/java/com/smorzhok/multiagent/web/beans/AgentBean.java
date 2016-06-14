package com.smorzhok.multiagent.web.beans;

import com.smorzhok.multiagent.common.ContextHolder;
import com.smorzhok.multiagent.common.model.ModelParamsFactory;
import com.smorzhok.multiagent.jade.JadeRunner;
import com.smorzhok.multiagent.sarl.SarlRunner;
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

import io.janusproject.kernel.Kernel;
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
    private AgentContainer jadeContainer;
    private Kernel sarlKernel;

    public void setStatisticsBean(SimulationStatisticsBean statisticsBean) {
        this.statisticsBean = statisticsBean;
    }

    public AgentBean() {
        sessionId = Helper.getCurrentSession();
        PushRenderer.addCurrentSession(sessionId);
    }

    public void runJadeSimulation() throws Exception {
        runSimulation(SimulationType.JADE);
    }

    public void runSarlSimulation() throws Exception {
        runSimulation(SimulationType.SARL);
    }

    public void runSimulation(SimulationType type) throws Exception {
        PortableRenderer renderer = PushRenderer.getPortableRenderer();
        statisticsBean.setRenderer(renderer);
        statisticsBean.setSessionId(sessionId);
        statisticsBean.reset();
        switch (type) {
            case JADE:
                jadeContainer = JadeRunner.run(modelParamsFactory.defaultModelParams(), statisticsBean);
                break;
            case SARL:
                sarlKernel = SarlRunner.run(modelParamsFactory.defaultModelParams(), statisticsBean);
                break;
            default:
                throw new IllegalArgumentException("Unknown simulation type");
        }
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "startUpdating()");
    }

    public void stopSimulation() throws Exception {
        if (jadeContainer != null) {
            jadeContainer.kill();
            jadeContainer = null;
        } else if (sarlKernel != null) {
            SarlRunner.kill(sarlKernel);
            sarlKernel = null;
        }
        JavaScriptRunner.runScript(FacesContext.getCurrentInstance(), "abortTimer()");
    }

    private enum SimulationType {
        JADE,
        SARL
    }

}
