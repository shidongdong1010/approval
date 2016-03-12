package com.itanbank.account.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by SDD on 2016/2/29.
 */
public class ApplicationHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == applicationContext.getParent()) {//父容器的parent为null
            this.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return ApplicationHelper.applicationContext;
    }
}