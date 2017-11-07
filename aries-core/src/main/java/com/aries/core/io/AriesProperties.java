package com.aries.core.io;

public interface AriesProperties {
	String getProperty(String key);
	String getProperty(String key, String defaultValue);
	<T> T getProperty(String key, Class<T> classType);
}
