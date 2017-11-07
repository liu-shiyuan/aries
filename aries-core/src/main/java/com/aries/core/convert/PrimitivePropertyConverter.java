package com.aries.core.convert;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ClassUtils;

public class PrimitivePropertyConverter implements PropertyConverter {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(String source, Class<T> classType) {
		Class<?> type = ClassUtils.primitiveToWrapper(classType);
		Method method = getOfMethod(type);
		try {
			return (T) method.invoke(null, source);
		} catch (Exception e) {
			// TODO throws specify Exception
			return null;
		}
	}

	@Override
	public boolean matches(Class<?> classType) {
		return ClassUtils.isPrimitiveOrWrapper(classType);
	}

}
