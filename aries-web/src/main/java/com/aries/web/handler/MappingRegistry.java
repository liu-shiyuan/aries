package com.aries.web.handler;

import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import com.aries.web.annotation.Controller;
import com.aries.web.controller.AbstractController;

public abstract class MappingRegistry {
	
	public void register(String[] packages) {
		for (String pkg : packages) {
			register(pkg);
		}
	}
	
	public void register(Package pkg) {
		register(pkg.getName());
	}
	
	public void register(String pkg) {
		if (StringUtils.isEmpty(pkg))
			return;
		Reflections refs = new Reflections(pkg);
		Set<Class<? extends AbstractController>> extended = refs.getSubTypesOf(AbstractController.class);
		Set<Class<?>> annotated = refs.getTypesAnnotatedWith(Controller.class);
		
		if (CollectionUtils.isNotEmpty(extended)) {
			extendedRegister(extended);
		}
		
		if (CollectionUtils.isNotEmpty(annotated)) {
			annotatedRegister(annotated);
		}
		
	}
	
	protected void extendedRegister(Set<Class<? extends AbstractController>> extended) {
		for (Class<? extends AbstractController> clz : extended) {
			extendedRegister(clz);
		}
	}
	
	abstract void extendedRegister(Class<? extends AbstractController> clz);
	
	protected void annotatedRegister(Set<Class<?>> set) {
		for (Class<?> clz : set) {
			annotatedRegister(clz);
		}
	}
	
	abstract void annotatedRegister(Class<?> clz);

}
