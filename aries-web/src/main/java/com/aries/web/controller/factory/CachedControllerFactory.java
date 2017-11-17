package com.aries.web.controller.factory;

import java.util.Map;

import com.google.common.collect.Maps;

public class CachedControllerFactory implements ControllerFactory {
	
	private Map<Class<?>, Object> controllerMap;
	
	public CachedControllerFactory() {
		controllerMap = Maps.newConcurrentMap();
	}

	@Override
	public <T> T getController(Class<T> clz) {
		Object obj = controllerMap.get(clz);
		if (obj == null) {
			try {
				T t = clz.newInstance();
				controllerMap.put(clz, t);
				return t;
			} catch (InstantiationException | IllegalAccessException e) {
			}
		}
		return (T) obj;
	}

}
