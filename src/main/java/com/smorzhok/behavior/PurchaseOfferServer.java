package com.smorzhok.behavior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Behavior for Operators which handles request for purchasing tours
 *
 * @author Dmitry Smorzhok
 */
public class PurchaseOfferServer extends CyclicBehaviour {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOfferServer.class);

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            String country = msg.getContent();
            ACLMessage reply = msg.createReply();
            Integer price = 0; // TODO
            if (price != null) {
                reply.setPerformative(ACLMessage.INFORM);
                LOGGER.debug("Tour to " + country + " sold to agent " + msg.getSender().getName() + " for " + price);
            } else {
                reply.setPerformative(ACLMessage.FAILURE);
                reply.setContent("not-available");
            }
            myAgent.send(reply);
        } else {
            block();
        }
    }

}
