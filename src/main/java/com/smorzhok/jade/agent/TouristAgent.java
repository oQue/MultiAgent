package com.smorzhok.jade.agent;

import com.smorzhok.common.ModelParams;
import com.smorzhok.jade.behavior.RequestPerformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * A tourist agent which is going to vacation
 *
 * @author Dmitry Smorzhok
 */
public class TouristAgent extends Agent {

    private static final Logger LOGGER = LoggerFactory.getLogger(TouristAgent.class);

    /**
     * Tick period for an agent's vacation behavior (in milliseconds)
     */
    private static final long VACATION_PERIOD = 10 * 1000;
    /**
     * Tick period for an agent's monthly salary behavior (in milliseconds)
     */
    private static final long MONTH_PERIOD = 5 * 1000;
    /**
     * 28 holiday days per year. Average about 2.33 per month
     */
    private static final double HOLIDAY_PER_MONTH = 2.33;
    /**
     * the shortest possible vacation is 7 days long
     */
    private static final byte SHORTEST_VACATION = 7;

    private static final Random RANDOM = new Random();

    private ModelParams params;

    private double income;
    private double balance = 0.0;
    private double holidayDays = 0.0;

    @Override
    protected void setup() {
        LOGGER.debug("Tourist setup: " + getAID().getName());
        Object[] args = getArguments();
        if (args != null && args.length == 1) {
            params = (ModelParams) args[0];
            income = params.getAverageSalary();
            LOGGER.info("Monthly income: " + income + ". Current balance: " + balance);
            addBehaviour(new TickerBehaviour(this, VACATION_PERIOD) {
                @Override
                protected void onTick() {
                    if (readyForVacation()) {
                        startNegotiations();
                    }
                }
            });
            addBehaviour(new TickerBehaviour(this, MONTH_PERIOD) {
                @Override
                protected void onTick() {
                    monthPassed();
                }
            });
        } else {
            LOGGER.warn("Wrong args: " + Arrays.toString(args));
            doDelete();
        }
    }

    private void startNegotiations() {
        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        int operatorsAmount = params.getOperatorAmount();
        for (int i = 0; i < operatorsAmount; ++i) {
            cfp.addReceiver(new AID("operator" + i, AID.ISLOCALNAME));
        }
        cfp.setContent("France"); // TODO
        cfp.setConversationId("tour-request");
        cfp.setReplyWith(UUID.randomUUID().toString());
        cfp.setReplyByDate(new Date(System.currentTimeMillis() + 2000));
        addBehaviour(new RequestPerformer(this, cfp));
    }

    @Override
    protected void takeDown() {
        LOGGER.debug("Tourist agent " + getAID().getName() + " terminated");
    }

    private void monthPassed() {
        double coefficient = RANDOM.nextDouble();
        double spentThisMonth = income * coefficient;
        if (RANDOM.nextDouble() < 0.3) {
            // once in ~3 months we're spending up to 3 salaries for goodies
            int multiplier = RANDOM.nextInt(4);
            LOGGER.debug("Multiplier: " + multiplier);
            if (multiplier > 0) {
                spentThisMonth *= multiplier;
            }
        }
        balance += income - spentThisMonth;
        if (balance < 0) {
            balance = 0;
        }
        holidayDays += HOLIDAY_PER_MONTH;
        LOGGER.debug("Current balance: " + balance + " (" + getAID().getName() + ")");
    }

    private boolean readyForVacation() {
        return holidayDays > SHORTEST_VACATION && balance > income * 3;
    }

}
