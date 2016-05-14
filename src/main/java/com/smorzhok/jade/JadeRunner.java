package com.smorzhok.jade;

import com.smorzhok.common.DefaultModelParams;
import com.smorzhok.common.ModelCallback;
import com.smorzhok.common.ModelParams;
import com.smorzhok.jade.agent.OperatorAgent;
import com.smorzhok.jade.agent.StatisticsAgent;
import com.smorzhok.jade.agent.TouristAgent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * Main class for local test purposes
 * Creates main container for Jade and populates it with agents
 *
 * @author Dmitry Smorzhok
 */
public class JadeRunner {

    public static void main(String[] args) throws StaleProxyException {
        run(new DefaultModelParams(), null);
    }

    public static AgentContainer run(ModelParams params, ModelCallback callback) throws StaleProxyException {
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

        for (int i = 0; i < params.getTouristAmount(); i++) {
            AgentController agent = agentContainer.createNewAgent("tourist" + i, TouristAgent.class.getName(),
                    new Object[]{ params });
            agent.start();
        }

        for (int i = 0; i < params.getOperatorAmount(); i++) {
            AgentController operator = agentContainer.createNewAgent("operator" + i, OperatorAgent.class.getName(),
                    new Object[]{ params });
            operator.start();
        }

        return mainContainer;
    }

}
