package com.github.vole.portal.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationContext 工具类
 */
@Component
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext springContext;

    public static ApplicationContext getApplicationContext() {
        return springContext;
    }

    public BeanUtil() {

    }


    public static <T> T getBean(Class<T> requiredType) {

        if (springContext == null) {

            throw new RuntimeException("springContext is null.");
        }
        return springContext.getBean(requiredType);
    }

    public static Object getBeanName(String beanName) {

        if (springContext == null) {

            throw new RuntimeException("springContext is null.");
        }
        return springContext.getBean(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }
}
