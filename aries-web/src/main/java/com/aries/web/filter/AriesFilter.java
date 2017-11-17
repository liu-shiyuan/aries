package com.aries.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aries.core.config.Config;
import com.aries.core.io.AriesProperties;
import com.aries.web.exception.StartUpException;
import com.aries.web.handler.AriesHandlerDispatcher;
import com.aries.web.handler.HandlerDispather;

public class AriesFilter implements Filter {
	
	private HandlerDispather dispatcher;
	private AriesProperties env;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		env = (AriesProperties) filterConfig.getServletContext().getAttribute("env");
		Config config = createConfig();
		dispatcher = getHandlerDispatcher(config);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        dispatcher.doDispath(req, res);
	}

	@Override
	public void destroy() {
		
	}
	
	private Config createConfig() {
		String configClass = determineConfigClass(); 
		Config config = null;
		Object temp= null;
		try {
			temp = Class.forName(configClass).newInstance();
		} catch (Exception e) {
			throw new StartUpException("Can't instantiate config class of: " + configClass, e);
		}
		
		if (temp instanceof Config) {
			config = (Config) temp;
			return config;
		} else {
			throw new StartUpException("Can't instantiate config class of: " + configClass);
		}
		
		
	}
	
	private String determineConfigClass() {
		return env.getProperty("config.class");
	}
	
	private HandlerDispather getHandlerDispatcher(Config config) {
		HandlerDispather dispatcher = new AriesHandlerDispatcher();
		dispatcher.init(config);
		return dispatcher;
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.toBinaryString(22));
		
	}
	
	

}
