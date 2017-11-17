package com.aries.web.handler;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class PathMatcherHelper {
	
	private static final String PLACEHOLDER = "\\.\\*";
	private static final String VARIABLE_PATTERN = "\\{.*\\}";
	private static final String DEFAULT_PATH_SEPARATOR = "/";
	
	public String getPattern(String path) {
	
		path = path.replace("\\", DEFAULT_PATH_SEPARATOR);
		
		if (!path.startsWith(DEFAULT_PATH_SEPARATOR)) 
			path = DEFAULT_PATH_SEPARATOR + path;
		
		if (path.endsWith(DEFAULT_PATH_SEPARATOR)) 
			path = path.substring(0, path.length() - 1);
		
		return toPattern(path);
	}
	
	private String toPattern(String path) {
		String b[] = path.split(DEFAULT_PATH_SEPARATOR);
		StringBuilder sb = new StringBuilder(DEFAULT_PATH_SEPARATOR);
		List<String> list = Arrays.asList(b).stream().filter(StringUtils::isNotEmpty)
				.map(c -> c.replaceAll(VARIABLE_PATTERN, PLACEHOLDER)).collect(Collectors.toList());
		for (int i = 0; i < list.size() - 1; i++) {
			sb.append(list.get(i)).append(DEFAULT_PATH_SEPARATOR);
		}
		sb.append(list.get(list.size() - 1));
		return sb.toString();
	}
	
	public static void main(String[] args) {
		PathMatcherHelper h = new PathMatcherHelper();
		String c = h.getPattern("he/{x}/goo/{dc}/vj/{hi}");
		Pattern p = Pattern.compile(c);
		boolean b = p.matcher("/he/1xxx/goo/2dfd/vj/3ff").matches();
		System.out.println(b);
	}

}
