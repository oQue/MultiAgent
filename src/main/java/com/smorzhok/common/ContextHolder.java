package com.smorzhok.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring application context holder
 *
 * @author Dmitry Smorzhok
 */
public final class ContextHolder {

    private ContextHolder() {}

    public static ApplicationContext instance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        public static final ApplicationContext INSTANCE = new ClassPathXmlApplicationContext("classpath:context.xml");
    }

}
