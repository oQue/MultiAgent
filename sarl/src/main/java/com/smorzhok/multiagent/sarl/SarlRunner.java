package com.smorzhok.multiagent.sarl;

import com.google.inject.Module;

import com.smorzhok.multiagent.common.ContextHolder;
import com.smorzhok.multiagent.common.entity.ModelParam;
import com.smorzhok.multiagent.common.model.ModelCallback;
import com.smorzhok.multiagent.common.model.ModelParamsFactory;
import com.smorzhok.multiagent.sarl.BootAgent;
import com.smorzhok.multiagent.sarl.KillAgent;
import com.smorzhok.multiagent.sarl.StatisticsAgent;
import com.smorzhok.multiagent.sarl.agent.SarlAgentFactory;

import io.janusproject.Boot;
import io.janusproject.JanusConfig;
import io.janusproject.kernel.Kernel;

public class SarlRunner {

    private static Kernel kernel = null;

    private static ModelParamsFactory modelParamsFactory =
            (ModelParamsFactory) ContextHolder.instance().getBean("modelParamsFactory");

    public static void main(String[] args) throws Exception {
        run(modelParamsFactory.defaultModelParams(), null);
    }

    public static Kernel run(ModelParam params, ModelCallback callback) throws Exception {
        if (kernel == null) {
            Class<? extends Module> startupModule = JanusConfig.getSystemPropertyAsClass(
                    Module.class, JanusConfig.INJECTION_MODULE_NAME,
                    JanusConfig.INJECTION_MODULE_NAME_VALUE);
            kernel = Boot.startJanus(startupModule, BootAgent.class);
        }
        kernel.spawn(StatisticsAgent.class, callback);
        SarlAgentFactory.operatorAgents(kernel, params);
        SarlAgentFactory.touristAgents(kernel, params);
        return kernel;
    }

    public static void kill(Kernel kernel) throws Exception {
        kernel.spawn(KillAgent.class);
    }

}