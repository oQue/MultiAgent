package com.smorzhok.multiagent.sarl;

import com.google.inject.Module;

import com.smorzhok.multiagent.common.ContextHolder;
import com.smorzhok.multiagent.common.entity.ModelParam;
import com.smorzhok.multiagent.common.model.ModelCallback;
import com.smorzhok.multiagent.common.model.ModelParamsFactory;
import com.smorzhok.multiagent.sarl.BootAgent;
import com.smorzhok.multiagent.sarl.agent.SarlAgentFactory;

import io.janusproject.Boot;
import io.janusproject.JanusConfig;
import io.janusproject.kernel.Kernel;

public class SarlRunner {

    private static ModelParamsFactory modelParamsFactory =
            (ModelParamsFactory) ContextHolder.instance().getBean("modelParamsFactory");

    public static void main(String[] args) throws Exception {
        run(modelParamsFactory.defaultModelParams(), null, args);
    }

    public static void run(ModelParam params, ModelCallback callback, String[] args) throws Exception {
        Class<? extends Module> startupModule = JanusConfig.getSystemPropertyAsClass(
                Module.class, JanusConfig.INJECTION_MODULE_NAME,
                JanusConfig.INJECTION_MODULE_NAME_VALUE);
        Kernel kernel = Boot.startJanus(startupModule, BootAgent.class, args);
        SarlAgentFactory.operatorAgents(kernel, params);
        SarlAgentFactory.touristAgents(kernel, params);
    }

}