package com.aries.web.controller.mapping;

import java.util.Arrays;
import java.util.Objects;

import com.aries.web.constant.HttpMethod;

public class MappingInfo {
	
	private final String[] patterns;

	private final HttpMethod method;

	private final String[] headers;
	
	public MappingInfo(String[] patterns, HttpMethod method, String[] headers) {
		this.patterns = patterns;
		this.method = method;
		this.headers = headers;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (Objects.isNull(obj))
			return false;
		MappingInfo otherObj = (MappingInfo) obj;
		return Arrays.equals(this.patterns, otherObj.patterns) && Objects.equals(this.method, otherObj.method)
				&& Arrays.equals(this.headers, otherObj.headers);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(patterns) * 23 + Objects.hashCode(method) * 31 + Arrays.hashCode(headers) * 32;
	}
	
	
	
}
