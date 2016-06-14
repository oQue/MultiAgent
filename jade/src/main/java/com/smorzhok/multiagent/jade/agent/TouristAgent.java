package com.smorzhok.multiagent.jade.agent;

import com.smorzhok.multiagent.jade.behavior.RequestPerformer;

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

    private String type;
    private int operatorsAmount;
    private double income;
    private volatile double balance;
    private volatile double holidayDays;
    private double eurCurrencyRate;

    @Override
    protected void setup() {
        LOGGER.debug("Tourist setup: " + getAID().getName());
        Object[] args = getArguments();
        if (args != null && args.length == 4) {
            operatorsAmount = (Integer) args[0];
            income = (Double) args[1];
            type = (String) args[2];
            eurCurrencyRate = (Double) args[3];
            balance = income * RANDOM.nextInt(5);
            holidayDays = RANDOM.nextInt(20);
            LOGGER.debug("Monthly income: " + income + ". Current balance: " + balance +
                    ". Type: " + type + ". Holiday days: " + holidayDays);
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
        for (int i = 0; i < operatorsAmount; ++i) {
            cfp.addReceiver(new AID("operator" + i, AID.ISLOCALNAME));
        }
        cfp.setContent(type);
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
        synchronized (this) {
            balance += income - spentThisMonth;
            if (balance < 0) {
                balance = 0;
            }
            holidayDays += HOLIDAY_PER_MONTH;
        }
        LOGGER.debug("Current balance: " + balance + " (" + getAID().getName() + ")");
    }

    private boolean readyForVacation() {
        return holidayDays > SHORTEST_VACATION && balance > income * 3;
    }

    public synchronized boolean buyTour(double price, int duration) {
        price *= eurCurrencyRate;
        if (price > balance) {
            LOGGER.warn("Attempt to by a tour for " + price + " while having " + balance + " at balance");
            return false;
        } else if (duration > holidayDays) {
            LOGGER.warn("Attempt to by a tour for " + duration + " days while having " + holidayDays + " holiday days");
            return false;
        }
        this.balance -= price;
        this.holidayDays -= duration;
        return true;
    }

}
