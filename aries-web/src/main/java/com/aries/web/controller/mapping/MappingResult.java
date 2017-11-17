package com.aries.web.controller.mapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aries.web.controller.AbstractController;

public class MappingResult<T> {
	private Method method;
	private MappingInfo info;
	private T t;
	private HttpServletRequest req;
	private HttpServletResponse res;
	
	public MappingResult(Method method, MappingInfo info, T t, HttpServletRequest req, HttpServletResponse res) {
		this.method = method;
		this.info = info;
		this.t = t;
		this.req = req;
		this.res = res;
	}
	
	public Object simpleInvoke() {
		try {
			if (t instanceof AbstractController) {
				method.invoke(t, req, res);
				return null;
			} else {
				// TODO
				return method.invoke(t);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
