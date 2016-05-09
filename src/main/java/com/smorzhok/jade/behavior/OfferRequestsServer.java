package com.smorzhok.jade.behavior;

import com.smorzhok.common.StatisticsMessageContent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        ACLMessage message = myAgent.receive(mt);
        if (message != null) {
            String country = message.getContent();
            ACLMessage reply = message.createReply();
            double price = 0.0; // TODO
            reply.setPerformative(ACLMessage.PROPOSE);
            try {
                reply.setContentObject(new StatisticsMessageContent(price, country));
            } catch (IOException ex) {
                LOGGER.error("Couldn't set message content: ", ex);
            }
            LOGGER.debug("Proposal: Tour to " + country + " for " + price + "RUB");
            myAgent.send(reply);
        } else {
            block();
        }
    }

}
