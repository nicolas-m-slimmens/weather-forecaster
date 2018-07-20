package org.slimmens.weather.forecaster.service.utils;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class LogInjector implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = bean.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			if (!field.isAnnotationPresent(Log.class)) {
				continue;
			}

			try {
				field.setAccessible(true);
				field.set(bean, Logger.getLogger(clazz));
			} catch (Exception e) {
				throw new FatalBeanException(String.format("Unable to inject the logger to the bean %s.", beanName), e);
			}
		}

		return bean;
	}

}
