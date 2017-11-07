package com.aries.core.convert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.aries.util.ReflectionUtils;

public interface PropertyConverter {

	<T> T convert(String source, Class<T> classType);

	boolean matches(Class<?> classType);

	default <T> Method getOfMethod(Class<T> classType) {
		Method method;
		try {
			method = classType.getMethod("valueOf", String.class);
			if (null == method)
				method = classType.getMethod("of", String.class);
			if (null == method)
				method = classType.getMethod("from", String.class);
			ReflectionUtils.makeAccessible(method);
			return method;
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	public static List<PropertyConverter> converters() {
		List<PropertyConverter> converters = new ArrayList<>(2);
		converters.add(new IdentityPropertyConverter());
		converters.add(new PrimitivePropertyConverter());
		return converters;
	}
}
