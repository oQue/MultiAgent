package com.smorzhok.common.model;

import com.smorzhok.common.entity.ModelParam;
import com.smorzhok.common.service.SelectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for model parameters instances
 *
 * @author Dmitry Smorzhok
 */
@Component
public final class ModelParamsFactory {

    @Autowired
    private SelectionService selectionService;

    /**
     * Creates model filled with default parameters
     * @return default model
     */
    public ModelParam defaultModelParams() {
        ModelParam params = new ModelParam();
        params.setTouristAmount(10);
        params.setOperatorAmount(1);
        params.setAverageSalary(35000.00);
        return params;
    }

    /**
     * Returns custom model from database by given ID
     * @param id    model ID
     * @return      model from database
     */
    public ModelParam customModelParams(long id) {
        return selectionService.getObjectById(ModelParam.class, id);
    }

}
