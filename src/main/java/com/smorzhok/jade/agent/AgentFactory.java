package com.smorzhok.jade.agent;

import com.smorzhok.common.entity.ModelParam;

import java.util.ArrayList;
import java.util.List;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * Factory for initializing agents
 *
 * @author Dmitry Smorzhok
 */
public final class AgentFactory {

    private AgentFactory() {}

    /**
     * Creates a collection of tourist agents according to given model params
     * @param container agent container
     * @param params    model params
     * @return          list of agents
     * @throws StaleProxyException
     */
    public static List<AgentController> touristAgents(AgentContainer container, ModelParam params)
            throws StaleProxyException
    {
        List<AgentController> agents = new ArrayList<>();
        for (int i = 0; i < params.getTouristAmount(); i++) {
            AgentController tourist = container.createNewAgent("tourist" + i, TouristAgent.class.getName(),
                    new Object[]{ params.getOperatorAmount(), params.getAverageSalary() });
            agents.add(tourist);
        }
        return agents;
    }

    /**
     * Creates a collection of operator agents according to given model params
     * @param container agent container
     * @param params    model params
     * @return          list of agents
     * @throws StaleProxyException
     */
    public static List<AgentController> operatorAgents(AgentContainer container, ModelParam params)
            throws StaleProxyException
    {
        List<AgentController> agents = new ArrayList<>();
        for (int i = 0; i < params.getOperatorAmount(); i++) {
            AgentController operator = container.createNewAgent("operator" + i, OperatorAgent.class.getName(),
                    new Object[]{ params });
            agents.add(operator);
        }
        return agents;
    }

}
