package com.app.common.util;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * 在spring容器中获取指定的bean
 */
@Component
public class AppServiceHelper implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		AppServiceHelper.applicationContext = applicationContext;
	}

	public static Object findBean(String beanId)
			throws NoSuchBeanDefinitionException {
		Object service = applicationContext.getBean(beanId);
		return service;
	}

}
