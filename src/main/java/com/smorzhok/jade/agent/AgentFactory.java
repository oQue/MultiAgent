package com.smorzhok.jade.agent;

import com.smorzhok.common.entity.ModelParam;
import com.smorzhok.common.model.TouristType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * Factory for initializing agents
 *
 * @author Dmitry Smorzhok
 */
public final class AgentFactory {

    private static final Random RANDOM = new Random();

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
            double agentSalary = generateAgentSalary(params);
            TouristType type = assignTouristType(params, agentSalary);
            AgentController tourist = container.createNewAgent("tourist" + i, TouristAgent.class.getName(),
                    new Object[]{ params.getOperatorAmount(), agentSalary, type.getName(), params.getEurCurrencyRate() });
            agents.add(tourist);
        }
        return agents;
    }

    /**
     * Method which generates an agent salary using normal distribution
     * with average and deviation from model params
     *
     * @param params    model params
     * @return          agent's salary which is >= min salary from model params
     */
    private static double generateAgentSalary(ModelParam params) {
        double minSalary = params.getMinimumSalary();
        double average = params.getAverageSalary();
        double deviation = params.getSalaryDeviation();
        double salary;
        do {
            salary = RANDOM.nextGaussian() * deviation + average;
        } while (salary < minSalary);
        return salary;
    }

    /**
     * Assigns tourist type by agent's salary
     * If agents salary is 30% less than average he would probably be budget
     *      probability of budget is 0.6
     *      probability of regular is 0.35
     *      probability of luxury is 0.05
     * If agents salary is 40% above average he would probably be luxury
     *      probability of budget is 0.1
     *      probability of regular is 0.3
     *      probability of luxury is 0.6
     * Otherwise he would probably be regular
     *      probability of budget is 0.3
     *      probability of regular is 0.6
     *      probability of luxury is 0.1
     *
     * @param params        model params
     * @param agentSalary   agent's salary
     * @return              type of tourist agent
     */
    private static TouristType assignTouristType(ModelParam params, double agentSalary) {
        int budgetProbability;
        int regularProbability;
        int luxuryProbability;
        double averageSalary = params.getAverageSalary();
        if (agentSalary < averageSalary * 0.7) {
            budgetProbability = 60;
            regularProbability = 35;
            luxuryProbability = 5;
        } else if (agentSalary > averageSalary * 1.4) {
            budgetProbability = 10;
            regularProbability = 30;
            luxuryProbability = 60;
        } else {
            budgetProbability = 30;
            regularProbability = 60;
            luxuryProbability = 10;
        }
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            if (budgetProbability > 0) {
                array[i] = TouristType.BUDGET.ordinal();
                budgetProbability -= 1;
            } else if (regularProbability > 0) {
                array[i] = TouristType.REGULAR.ordinal();
                regularProbability -= 1;
            } else if (luxuryProbability > 0) {
                array[i] = TouristType.LUXURY.ordinal();
                luxuryProbability -= 1;
            }
        }
        int randomIndex = RANDOM.nextInt(100);
        int type = array[randomIndex];
        return TouristType.values[type];
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
