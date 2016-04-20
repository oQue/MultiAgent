package com.smorzhok;

import com.smorzhok.agent.OperatorAgent;
import com.smorzhok.agent.TouristAgent;

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
        Runtime rt = Runtime.instance();
        rt.setCloseVM(true);
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.MAIN_PORT, "10099");
        rt.createMainContainer(p);
        // separated container for agents
        AgentContainer agentContainer = rt.createAgentContainer(p);
        for (int i = 0; i < 10; i++) {
            AgentController agent = agentContainer.createNewAgent("tourist" + i, TouristAgent.class.getName(),
                    new Object[]{"10000", "0", "0"});
            agent.start();
        }
        AgentController operator = agentContainer.createNewAgent("operator", OperatorAgent.class.getName(), new Object[]{});
        operator.start();
    }

}
