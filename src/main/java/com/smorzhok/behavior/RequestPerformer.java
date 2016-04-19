package com.smorzhok.behavior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jade.core.behaviours.OneShotBehaviour;

/**
 * @author Dmitry Smorzhok
 */
public class RequestPerformer extends OneShotBehaviour {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestPerformer.class);

    @Override
    public void action() {
        LOGGER.debug("Inside RequestPerformer");
    }

}
