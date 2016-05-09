package com.smorzhok.behavior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
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
        int bestProposal = -1;
        AID bestProposer = null;
        ACLMessage accept = null;
        Enumeration e = responses.elements();
        while (e.hasMoreElements()) {
            ACLMessage msg = (ACLMessage) e.nextElement();
            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                acceptances.addElement(reply);
                int proposal = Integer.parseInt(msg.getContent());
                if (proposal > bestProposal) {
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
    }

}
