package com.smorzhok.multiagent.sarl;

import com.google.inject.Module;

import com.smorzhok.multiagent.common.ContextHolder;
import com.smorzhok.multiagent.common.entity.ModelParam;
import com.smorzhok.multiagent.common.model.ModelCallback;
import com.smorzhok.multiagent.common.model.ModelParamsFactory;
import com.smorzhok.multiagent.sarl.BootAgent;
import com.smorzhok.multiagent.sarl.StatisticsAgent;
import com.smorzhok.multiagent.sarl.agent.SarlAgentFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.janusproject.Boot;
import io.janusproject.JanusConfig;
import io.janusproject.kernel.Kernel;
import io.janusproject.services.IServiceManager;
import io.janusproject.services.Services;
import io.janusproject.services.spawn.SpawnService;
import io.sarl.lang.core.Agent;

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

    @SuppressWarnings("unchecked")
    public static void kill() throws Exception {
        SpawnService spawnService = getFieldByReflection(kernel, "spawnService", SpawnService.class);
        Map<UUID, Agent> agents = new HashMap<UUID, Agent>(getFieldByReflection(spawnService, "agents", Map.class));
        for (Map.Entry<UUID, Agent> entry : agents.entrySet()) {
            if (entry.getValue() instanceof BootAgent) {
                continue;
            }
            spawnService.killAgent(entry.getKey());
        }
    }

    private static <T> T getFieldByReflection(Object source, String fieldName, Class<T> clazz) throws Exception {
        Field f = source.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return clazz.cast(f.get(source));
    }

}