package com.aries.web.handler;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;

import com.aries.web.annotation.GET;
import com.aries.web.annotation.POST;
import com.aries.web.annotation.Path;
import com.aries.web.constant.HttpMethod;
import com.aries.web.controller.AbstractController;
import com.aries.web.controller.factory.ControllerFactory;
import com.aries.web.controller.mapping.MappingInfo;
import com.aries.web.controller.mapping.MappingResult;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

public class RequestMappingRegistry extends MappingRegistry {
	
	private Map<MappingInfo, Method> mappingMethods;
//	private List<Class<? extends AbstractController>> extendedClasses;
//	private List<Class<?>> annotatedClasses;
	private Multimap<String, MappingInfo> urlMap;
	private ControllerFactory controllerFactory;
	private PathMatcherHelper matcherHelper = new PathMatcherHelper();
	private Map<Pattern, String> patternMap;
	private Map<Method, Class<?>> methodTypeMap;
	
	private static final String DEFAULT_HANDLER_METHOD = "handleRequest";
	
	public RequestMappingRegistry(ControllerFactory factory) {
		mappingMethods = Maps.newHashMap();
//		extendedClasses = Lists.newArrayList();
//		annotatedClasses = Lists.newArrayList();
		urlMap = ArrayListMultimap.create();
		methodTypeMap = Maps.newHashMap();
		controllerFactory = factory;
	}
	
	public void extendedRegister(Class<? extends AbstractController> clz) {
		AbstractController controller = controllerFactory.getController(clz);
		String contentType = controller.getContentType();
		HttpMethod method = controller.getMethod();
		String path = controller.getPath();
		// String[] patterns, HttpMethod method, String[] headers
		MappingInfo info = new MappingInfo(new String[] {path}, method, new String[] {"Content-Type: " + contentType});
		try {
			Method handleMethod = clz.getMethod(DEFAULT_HANDLER_METHOD, new Class[] {HttpServletRequest.class, HttpServletResponse.class});
			mappingMethods.put(info, handleMethod);
			methodTypeMap.put(handleMethod, clz);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		String pattern = matcherHelper.getPattern(path);
		urlMap.put(pattern, info);
	}
	
	
	public void annotatedRegister(Class<?> clz) {
		Path[] ps = clz.getAnnotationsByType(Path.class);
		String[] prefixPath = null;
		if (ArrayUtils.isNotEmpty(ps)) {
			prefixPath = ps[0].value();
		}
		
		Method[] methods = clz.getDeclaredMethods();
		for (Method method : methods) {
			GET[] getMethods = method.getDeclaredAnnotationsByType(GET.class);
			if (ArrayUtils.isNotEmpty(getMethods)) {
				registerGET(prefixPath, getMethods[0], method, clz);
			}
		}
		
		for (Method method : methods) {
			POST[] getMethods = method.getDeclaredAnnotationsByType(POST.class);
			if (ArrayUtils.isNotEmpty(getMethods)) {
				registerPOST(prefixPath, getMethods[0], method, clz);
			}
		}
		
	}
	
	private void registerGET(String[] prefixPath, GET httpGET, Method method, Class<?> clz) {
		String[] suffixPath = httpGET.value();
		String[] paths = getAllPaths(prefixPath, suffixPath);
		
		MappingInfo info = new MappingInfo(paths, HttpMethod.GET, null);
		mappingMethods.put(info, method);
		methodTypeMap.put(method, clz);
		
		for (String path : paths) {
			String pattern = matcherHelper.getPattern(path);
			urlMap.put(pattern, info);
		}
	}
	
	private void registerPOST(String[] prefixPath, POST httpPOST, Method method, Class<?> clz) {
		String[] suffixPath = httpPOST.value();
		String[] paths = getAllPaths(prefixPath, suffixPath);
		
		MappingInfo info = new MappingInfo(paths, HttpMethod.POST, null);
		mappingMethods.put(info, method);
		methodTypeMap.put(method, clz);
		
		for (String path : paths) {
			String pattern = matcherHelper.getPattern(path);
			urlMap.put(pattern, info);
		}
	}
	
	private String[] getAllPaths(String[] prefixPath, String[] suffixPath) {
		if (ArrayUtils.isEmpty(prefixPath)) {
			return suffixPath;
		} else {
			List<String> list = Lists.newArrayList();
			for (String prefix : prefixPath) {
				for (String suffix : suffixPath) {
					list.add(concatPath(prefix, suffix));
				}
			}
			return list.toArray(new String[list.size()]);
		}
	}
	
	private String concatPath(String prefix, String suffix) {
		if (prefix.endsWith("/") || prefix.endsWith("\\"))
			prefix = prefix.substring(0, prefix.length() - 1);
		if (suffix.startsWith("/") || suffix.startsWith("\\"))
			suffix = suffix.substring(1, suffix.length());
		return prefix + "/" + suffix;
	}
	
	public MappingResult<?> getMappingResult(HttpServletRequest request, HttpServletResponse response) {
		String uri = getRequestURI(request);
		Map<Pattern, String> pmap = getPatternMap();
		for (Pattern p : pmap.keySet()) {
			if (p.matcher(uri).matches()) {
				Collection<MappingInfo> infos = urlMap.get(uri);
				// TODO
				if (!infos.isEmpty()) {
					MappingInfo info = infos.iterator().next();
					Method method = mappingMethods.get(info);
					Class<?> clz = methodTypeMap.get(method);
					return new MappingResult(method, info, controllerFactory.getController(clz), request, response);
				}
			}
		}
		return null;
	}
	
	private String getRequestURI(HttpServletRequest request) {
		String uri = request.getRequestURI();
		int index = uri.indexOf("?");
		if (index != -1)
			uri = uri.substring(0, index);
		if (uri.endsWith("/") || uri.endsWith("\\"))
			uri = uri.substring(0, uri.length() - 1);
		return uri;
	}
	
	private Map<Pattern, String> getPatternMap() {
		if (Objects.isNull(patternMap)) {
			patternMap = Maps.newHashMap();
			for (String url : urlMap.keySet()) {
				Pattern p = Pattern.compile(url);
				patternMap.put(p, url);
			}
		}
		return patternMap;
	}

}
