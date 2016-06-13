package com.smorzhok.multiagent.common.agent;

import com.smorzhok.multiagent.common.entity.ModelParam;
import com.smorzhok.multiagent.common.model.TouristType;

import java.util.Random;

/**
 * Abstract factory for initializing agents
 *
 * @author Dmitry Smorzhok
 */
public abstract class AgentFactory {

    private static final Random RANDOM = new Random();

    /**
     * Method which generates an agent salary using normal distribution
     * with average and deviation from model params
     *
     * @param params    model params
     * @return          agent's salary which is >= min salary from model params
     */
    protected static double generateAgentSalary(ModelParam params) {
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
    protected static TouristType assignTouristType(ModelParam params, double agentSalary) {
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

}
