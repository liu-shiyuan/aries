package com.aries.web.controller;

import org.apache.commons.lang3.StringUtils;

import com.aries.web.constant.HttpMethod;

public abstract class AbstractController implements RestController {
	
	private HttpMethod method;
	private String contentType;
	private String path;
	
	public abstract HttpMethod supportedMethod();
	
	public abstract String mappingPath();
	
	public HttpMethod getMethod() {
		if (null == method)
			method = supportedMethod();
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getPath() {
		if (StringUtils.isEmpty(path))
			path = mappingPath();
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getContentType() {
		return contentType;
	}
	
}
