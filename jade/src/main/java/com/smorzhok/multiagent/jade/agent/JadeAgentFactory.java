package com.smorzhok.multiagent.jade.agent;

import com.smorzhok.multiagent.common.agent.AgentFactory;
import com.smorzhok.multiagent.common.entity.ModelParam;
import com.smorzhok.multiagent.common.model.TouristType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * Factory for initializing JADE agents
 *
 * @author Dmitry Smorzhok
 */
public final class JadeAgentFactory extends AgentFactory {

    private JadeAgentFactory() {}

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
            double agentSalary = generateAgentSalary(params);
            TouristType type = assignTouristType(params, agentSalary);
            AgentController tourist = container.createNewAgent("tourist" + i, TouristAgent.class.getName(),
                    new Object[]{ params.getOperatorAmount(), agentSalary, type.getName(), params.getEurCurrencyRate() });
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
