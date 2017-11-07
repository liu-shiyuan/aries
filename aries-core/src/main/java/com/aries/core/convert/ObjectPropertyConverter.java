package com.aries.core.convert;

import org.apache.commons.lang3.ClassUtils;

public class ObjectPropertyConverter implements PropertyConverter {

	@Override
	public <T> T convert(String source, Class<T> classType) {
		return null;
	}

	@Override
	public boolean matches(Class<?> classType) {
		if (ClassUtils.isPrimitiveOrWrapper(classType) || classType.isArray())
			return false;
		else
			return true;
	}

}
