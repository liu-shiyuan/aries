package com.aries.core.io;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.aries.core.convert.PropertyConverter;
import com.google.common.collect.Lists;

public abstract class AbstractAriesProperties implements AriesProperties, PropertiesLoader {

	private Properties properties;
	private static final List<PropertyConverter> converters = PropertyConverter.converters();
	private static final String DEFAULT_PROPERTIS_DELIMITER = ",";

	public AbstractAriesProperties() {
		properties = new Properties();
		List<Resource> sources = getPropertyResources();
		if (CollectionUtils.isNotEmpty(sources)) {
			for (Resource r : sources) {
				Properties p = new Properties();
				try {
					p.load(r.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				properties.putAll(p);
			}
		}
	}

	@Override
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		String value = properties.getProperty(key);
		if (StringUtils.isEmpty(value))
			return defaultValue;
		else
			return value;
	}

	@Override
	public <T> T getProperty(String key, Class<T> classType) {
		String val = getProperty(key);
		if (StringUtils.isNotEmpty(val)) {
			return getConvertedValue(val, classType);
		}
		return null;

	}
	
	public <T> List<T> getProperties(String key, Class<T> elementType) {
		return getProperties(key , elementType, DEFAULT_PROPERTIS_DELIMITER);
		
	}
	
	public <T> List<T> getProperties(String key, Class<T> elementType, String delimiter) {
		String val = getProperty(key);
		if (StringUtils.isEmpty(val)) {
			return null;
		}
		String[] vals = val.split(delimiter);
		List<T> elements = Lists.newArrayListWithCapacity(vals.length);
		T element;
		for (String v : vals) {
			if (StringUtils.isNoneEmpty(v.trim())) {
				element = getConvertedValue(v.trim(), elementType);
				if (element != null) {
					elements.add(element);
				}
			}
		}
		return elements.size() != 0 ? elements : null;
	}
	
	private <T> T getConvertedValue(String val, Class<T> classType) {
		for (PropertyConverter c : converters) {
			if (c.matches(classType)) {
				return c.convert(val, classType);
			}
		}
		return null;
	}
	
	

}
