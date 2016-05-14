package com.smorzhok.jade.agent;

import com.smorzhok.common.ModelParams;
import com.smorzhok.jade.behavior.OfferRequestsServer;
import com.smorzhok.jade.behavior.PurchaseOfferServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import jade.core.Agent;

/**
 * Operator agent which offers tours to Tourist Agents
 *
 * @author Dmitry Smorzhok
 */
public class OperatorAgent extends Agent {

    private ModelParams params;

    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorAgent.class);

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length == 1) {
            params = (ModelParams) args[0];
        } else {
            LOGGER.warn("Wrong args: " + Arrays.toString(args));
            doDelete();
        }
        addBehaviour(new OfferRequestsServer());
        addBehaviour(new PurchaseOfferServer());
    }

}
