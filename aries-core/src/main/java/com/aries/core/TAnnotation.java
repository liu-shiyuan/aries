package com.aries.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.aries.core.annotation.Config;

public class TAnnotation {

	public static List<Method> g(Class<?> clazz) {
		return Arrays
                .stream(clazz.getDeclaredMethods())
                .filter(method -> !method.isBridge())
                .collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		Map<Method, Class<?>> x = TAnnotation.f(ATest.class);
		// {public void com.aries.core.ATest.xx()=interface org.junit.After}
		System.out.println();
		Class<?> c = TAnnotation.x(x);
		System.out.println();
		Set<String> set = TAnnotation.q(c);
		System.out.println(set);
	}
	
	public static Map<Method, Class<?>> f(Class<?> claz) {
		 Map<Method, Class<?>> controllerMethods = new LinkedHashMap<>();

	        // discover all annotated controllers methods
	        for (Method method : TAnnotation.g(claz)) {
	            for (Annotation annotation : method.getAnnotations()) {
	                Class<? extends Annotation> annotationClass = annotation.annotationType();
	                controllerMethods.put(method, annotationClass);
	            }
	        }

	        return controllerMethods;
	}
	
	public static Class<?> x(Map<Method, Class<?>> controllerMethods) {
		Class<?> c = (Class<?>) controllerMethods.keySet().iterator().next().getDeclaringClass();
		return c;
	}
	
	public static Set<String> q(Class<?> c) {
        Set<String> parentPaths = Collections.emptySet();
        if (c.getSuperclass() != null) {
            parentPaths = q(c.getSuperclass());
        }
        Set<String> paths = new LinkedHashSet<>();
        Config a = c.getAnnotation(Config.class);
        
      /*  if (a != null) 
        	if (parentPaths.isEmpty())
        		paths.add(a.value());
        	else 
        		for (String s : parentPaths)
        			paths.add(s + a.value());*/
        

        return paths;
    }
}
