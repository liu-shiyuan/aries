package com.aries.core.convert;

public class IdentityPropertyConverter implements PropertyConverter {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(String source, Class<T> classType) {
		return (T) source;
	}

	@Override
	public boolean matches(Class<?> classType) {
		if (String.class.equals(classType))
			return true;
		else 
			return false;
	}

}
