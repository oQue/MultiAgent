package com.smorzhok.multiagent.jade.behavior;

import com.smorzhok.multiagent.common.StatisticsMessageContent;
import com.smorzhok.multiagent.common.entity.CountryParam;
import com.smorzhok.multiagent.common.entity.ModelParam;
import com.smorzhok.multiagent.common.model.TouristType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Behavior for Operators which handles request for booking tours from Tourists
 *
 * @author Dmitry Smorzhok
 */
public class OfferRequestsServer extends CyclicBehaviour {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferRequestsServer.class);

    private static final Random RANDOM = new Random();

    private static final int[] DURATIONS = new int[] { 7, 10, 14, 21 };

    private final ModelParam modelParam;

    public OfferRequestsServer(ModelParam modelParam) {
        this.modelParam = modelParam;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        ACLMessage message = myAgent.receive(mt);
        if (message != null) {
            ACLMessage reply = prepareReply(message);
            if (reply != null) {
                myAgent.send(reply);
            }
        } else {
            block();
        }
    }

    private ACLMessage prepareReply(ACLMessage message) {
        String type = message.getContent();
        double averageTourPricePerDay = modelParam.getAveragePricePerDay();
        TouristType touristType = TouristType.fromString(type);
        CountryParam countryParam = chooseCountry(averageTourPricePerDay, touristType);
        ACLMessage reply = message.createReply();
        if (countryParam != null) {
            String country = countryParam.getCountry().getName();
            int duration = DURATIONS[RANDOM.nextInt(DURATIONS.length)];
            double price = calculatePrice(countryParam, duration, touristType);
            reply.setPerformative(ACLMessage.PROPOSE);
            try {
                reply.setContentObject(new StatisticsMessageContent(price, country, duration, countryParam.getAttractivenessCoefficient()));
            } catch (IOException ex) {
                LOGGER.error("Couldn't set message content: ", ex);
            }
            LOGGER.debug("Proposal: Tour to " + country + " for " + price + "RUB");
        } else {
            reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
        }
        return reply;
    }

    /**
     * Choose country from an options for current agent. For a budget tourist
     * an upper-bound price is an average tour price. Luxury agents are looking
     * for a tours which are at least 30% above average price. Regulars have
     * equal chances for all destinations
     *
     * @param averageTourPricePerDay    average price per day among all destinations
     * @param touristType               type of tourist agent
     * @return                          random country for current agent type
     */
    private CountryParam chooseCountry(double averageTourPricePerDay, TouristType touristType) {
        Double upperBound = null;
        Double bottomBound = null;
        switch (touristType) {
            case BUDGET:
                upperBound = averageTourPricePerDay;
                break;
            case REGULAR:
                break;
            case LUXURY:
                bottomBound = averageTourPricePerDay * 1.3;
                break;
            default:
                throw new IllegalArgumentException("TouristType " + touristType.getName() + " is not supported");
        }
        List<CountryParam> countryParams = new ArrayList<>();
        if (upperBound != null || bottomBound != null) {
            for (CountryParam param : modelParam.getCountryParams()) {
                if (upperBound != null) {
                    if (param.getAveragePricePerDay() <= upperBound) {
                        countryParams.add(param);
                    }
                } else {
                    if (param.getAveragePricePerDay() >= bottomBound) {
                        countryParams.add(param);
                    }
                }
            }
        } else {
            countryParams.addAll(modelParam.getCountryParams());
        }
        boolean empty = CollectionUtils.isEmpty(countryParams);
        return empty ? null : countryParams.get(RANDOM.nextInt(countryParams.size()));
    }

    /**
     * Calculates final price for a tour. Price is calculated as average price times duration
     * plus random markup (dependent on agent type). In a rare cases (5%) tour is sold for
     * a special price which can be 20% low compared to average
     *
     * @param countryParam  country model parameters
     * @param duration      tour duration
     * @param touristType   type of tourist agent
     * @return              final tour price
     */
    private double calculatePrice(CountryParam countryParam, int duration, TouristType touristType) {
        double averagePerDay = countryParam.getAveragePricePerDay();
        double price = averagePerDay * duration;
        double coefficient = RANDOM.nextDouble();
        boolean specialPrice = RANDOM.nextDouble() < 0.05;
        if (!specialPrice) {
            if (TouristType.BUDGET == touristType) {
                if (coefficient > 0.2) {
                    coefficient = 0.2;
                }
            } else if (TouristType.REGULAR == touristType) {
                if (coefficient > 0.4) {
                    coefficient = 0.4;
                }
            }
        } else {
            if (coefficient < 0.8) {
                coefficient = 0.8;
            }
        }
        return specialPrice ? price * coefficient : price / coefficient;
    }

}
