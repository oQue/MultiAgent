package com.smorzhok.behavior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

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
    protected void handleAllResponses(Vector responses, Vector acceptances) {
        // TODO select best offer and send accept proposal response to best operator
        super.handleAllResponses(responses, acceptances);
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        LOGGER.info("Agent " + myAgent.getName() + " just bought a tour!");
    }

}
