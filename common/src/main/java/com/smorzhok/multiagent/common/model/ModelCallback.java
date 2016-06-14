package com.smorzhok.multiagent.common.model;

import com.smorzhok.multiagent.common.StatisticsMessageContent;

/**
 * Callback object for model
 *
 * @author Dmitry Smorzhok
 */
public interface ModelCallback {

    void update(StatisticsMessageContent content);

}
