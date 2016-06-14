package com.smorzhok.multiagent.sarl.agent;

import com.smorzhok.multiagent.common.agent.AgentFactory;
import com.smorzhok.multiagent.common.entity.ModelParam;
import com.smorzhok.multiagent.common.model.TouristType;
import com.smorzhok.multiagent.sarl.TouristAgent;
import com.smorzhok.multiagent.sarl.OperatorAgent;

import io.janusproject.kernel.Kernel;

/**
 * Factory for initializing Sarl agents
 *
 * @author Dmitry Smorzhok
 */
public class SarlAgentFactory extends AgentFactory {

    private SarlAgentFactory() {}

    /**
     * Spawns a collection of tourist agents according to given model params
     * @param kernel    janus kernel
     * @param params    model params
     */
    public static void touristAgents(Kernel kernel, ModelParam params) {
        int operatorsAmount = params.getOperatorAmount();
        double eurRate = params.getEurCurrencyRate();
        for (int i = 0; i < params.getTouristAmount(); i++) {
            double agentSalary = generateAgentSalary(params);
            TouristType type = assignTouristType(params, agentSalary);
            kernel.spawn(TouristAgent.class, operatorsAmount, agentSalary, type.getName(), eurRate);
        }
    }

    /**
     * Spawns a collection of operator agents according to given model params
     * @param kernel    janus kernel
     * @param params    model params
     */
    public static void operatorAgents(Kernel kernel, ModelParam params) {
        for (int i = 0; i < params.getOperatorAmount(); i++) {
            kernel.spawn(OperatorAgent.class, params);
        }
    }

}
