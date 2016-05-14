package com.smorzhok.common.model;

import com.smorzhok.common.StatisticsMessageContent;

/**
 * Callback object for model
 *
 * @author Dmitry Smorzhok
 */
public interface ModelCallback {

    void update(StatisticsMessageContent content);

}
