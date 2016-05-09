package com.smorzhok.jade.behavior;

import com.smorzhok.common.StatisticsMessageContent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetInitiator;

/**
 * Behavior of initiator role in a FIPA-contract-net
 *
 * @author Dmitry Smorzhok
 */
public class RequestPerformer extends ContractNetInitiator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestPerformer.class);

    public RequestPerformer(Agent agent, ACLMessage cfp) {
        super(agent, cfp);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void handleAllResponses(Vector responses, Vector acceptances) {
        Double bestProposal = null;
        AID bestProposer = null;
        ACLMessage accept = null;
        Enumeration e = responses.elements();
        while (e.hasMoreElements()) {
            ACLMessage msg = (ACLMessage) e.nextElement();
            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                acceptances.addElement(reply);
                StatisticsMessageContent content;
                try {
                    content = (StatisticsMessageContent) msg.getContentObject();
                    reply.setContentObject(content);
                } catch (Exception ex) {
                    LOGGER.error("Couldn't read message content: ", ex);
                    continue;
                }
                if (content == null) {
                    continue;
                }
                double proposal = content.getPrice();
                if (bestProposal == null || proposal < bestProposal) {
                    bestProposal = proposal;
                    bestProposer = msg.getSender();
                    accept = reply;
                }
            }
        }
        // Accept the proposal of the best proposer
        if (accept != null) {
            LOGGER.info("Accepting proposal " + bestProposal + " from responder " + bestProposer.getName());
            accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        }
        super.handleAllResponses(responses, acceptances);
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        LOGGER.info("Agent " + myAgent.getName() + " just bought a tour!");
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID("statistics", AID.ISLOCALNAME));
        try {
            message.setContentObject(inform.getContentObject());
        } catch (Exception ex) {
            LOGGER.error("Failed to set content: ", ex);
        }
        myAgent.send(message);
    }

}
