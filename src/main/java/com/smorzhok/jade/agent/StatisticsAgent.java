package com.smorzhok.jade.agent;

import com.smorzhok.common.model.ModelCallback;
import com.smorzhok.common.StatisticsMessageContent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Agent which gathers statistics from other agents
 *
 * @author Dmitry Smorzhok
 */
public class StatisticsAgent extends Agent {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsAgent.class);

    private ModelCallback callback;

    @Override
    protected void setup() {
        super.setup();
        Object[] args = getArguments();
        if (args.length != 1) {
            throw new IllegalArgumentException("Session id should be passed as an argument");
        }
        this.callback = (ModelCallback) args[0];
        addBehaviour(new StatisticsServer());
    }

    private class StatisticsServer extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage message = myAgent.receive(mt);
            if (message != null && callback != null) {
                try {
                    StatisticsMessageContent content = (StatisticsMessageContent) message.getContentObject();
                    callback.update(content);
                } catch (Exception e) {
                    LOGGER.error("Error while trying to update model: ", e);
                }
            } else {
                block();
            }
        }
    }

}
