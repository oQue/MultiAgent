package com.smorzhok.agent;

import com.smorzhok.behavior.OfferRequestsServer;
import com.smorzhok.behavior.PurchaseOfferServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jade.core.Agent;

/**
 * Operator agent which offers tours to Tourist Agents
 *
 * @author Dmitry Smorzhok
 */
public class OperatorAgent extends Agent {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorAgent.class);

    @Override
    protected void setup() {
        addBehaviour(new OfferRequestsServer());
        addBehaviour(new PurchaseOfferServer());
    }

}
