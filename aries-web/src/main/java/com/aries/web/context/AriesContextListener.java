package com.aries.web.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AriesContextListener implements ServletContextListener {
	
	private AriesWebContext ariesContext;
	private final static String ARIES_CONTEXT_PROPERTIES = "env";
	
	public AriesContextListener(AriesWebContext ac) {
		this.ariesContext = ac;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		sc.setAttribute(ARIES_CONTEXT_PROPERTIES, ariesContext.getEnv());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
