package com.smorzhok.jade.behavior;

import com.smorzhok.common.StatisticsMessageContent;

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
            ACLMessage reply = msg.createReply();
            StatisticsMessageContent content;
            try {
                content = (StatisticsMessageContent) msg.getContentObject();
                reply.setContentObject(content);
            } catch (Exception ex) {
                LOGGER.error("Failed to set message content: ", ex);
                return;
            }
            reply.setPerformative(ACLMessage.INFORM);
            myAgent.send(reply);
        } else {
            block();
        }
    }

}
