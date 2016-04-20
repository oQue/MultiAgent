package com.smorzhok.behavior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            Integer price = 0; // TODO
            if (price != null) {
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(String.valueOf(price.intValue()));
                LOGGER.debug("Proposal: Tour to " + country + " for " + price + "RUB");
            } else {
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("not-available");
            }
            myAgent.send(reply);
        } else {
            block();
        }
    }

}
