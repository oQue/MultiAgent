package com.smorzhok.jade;

import com.smorzhok.common.ContextHolder;
import com.smorzhok.common.entity.ModelParam;
import com.smorzhok.common.model.ModelCallback;
import com.smorzhok.common.model.ModelParamsFactory;
import com.smorzhok.jade.agent.AgentFactory;
import com.smorzhok.jade.agent.StatisticsAgent;

import java.util.List;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * Creates main container for Jade and populates it with agents
 *
 * @author Dmitry Smorzhok
 */
public class JadeRunner {

    private static ModelParamsFactory modelParamsFactory =
            (ModelParamsFactory) ContextHolder.instance().getBean("modelParamsFactory");

    public static void main(String[] args) throws StaleProxyException {
        run(modelParamsFactory.defaultModelParams(), null);
    }

    public static AgentContainer run(ModelParam params, ModelCallback callback) throws StaleProxyException {
        Runtime rt = Runtime.instance();
        rt.setCloseVM(true);
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.MAIN_PORT, "10099");
        AgentContainer mainContainer = rt.createMainContainer(p);
        // separated container for agents
        AgentContainer agentContainer = rt.createAgentContainer(p);

        AgentController statistics = agentContainer.createNewAgent("statistics", StatisticsAgent.class.getName(),
                new Object[] { callback });
        statistics.start();

        List<AgentController> operatorAgents = AgentFactory.operatorAgents(agentContainer, params);
        for (AgentController operator : operatorAgents) {
            operator.start();
        }

        List<AgentController> touristAgents = AgentFactory.touristAgents(agentContainer, params);
        for (AgentController tourist : touristAgents) {
            tourist.start();
        }

        return mainContainer;
    }

}
