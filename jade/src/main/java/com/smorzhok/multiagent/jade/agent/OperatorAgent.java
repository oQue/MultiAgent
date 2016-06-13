package com.smorzhok.multiagent.jade.agent;

import com.smorzhok.multiagent.common.entity.ModelParam;
import com.smorzhok.multiagent.jade.behavior.OfferRequestsServer;
import com.smorzhok.multiagent.jade.behavior.PurchaseOfferServer;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorAgent.class);

    @Override
    protected void setup() {
        Object[] args = getArguments();
        ModelParam modelParam;
        if (args != null && args.length == 1) {
            modelParam = (ModelParam) args[0];
        } else {
            LOGGER.warn("Wrong args: " + Arrays.toString(args));
            doDelete();
            return;
        }
        addBehaviour(new OfferRequestsServer(modelParam));
        addBehaviour(new PurchaseOfferServer());
    }

}
