package com.aries.web.server;

import java.util.Objects;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aries.web.context.AriesWebContext;
import com.aries.web.filter.AriesFilter;

public abstract class AbstractAriesServer<T extends ServerSettings> implements AriesServer {
	
	private Logger logger = LoggerFactory.getLogger(AriesServer.class);
	private Filter filter;
	
	private AriesWebContext context;
	private T settings;
	
	public AbstractAriesServer(AriesWebContext context) {
		this.context = context;
		onShutdownHook();
	}
	
	public AriesWebContext getContext() {
		return context;
	}
	
	protected abstract T initSettings();
	
	public T getSettings() {
		if (Objects.nonNull(settings))
			return settings;
		else {
			settings = initSettings();
			return settings;
		}
	}
	
	private void onShutdownHook() {
		logger.info("Stopping...");
		Runtime.getRuntime().addShutdownHook(new Thread(() -> stop()));
	}
	
	public Filter getAriesFilter() {
		return filter != null ? filter : createAriesFilter();
	}
	
	private Filter createAriesFilter() {
		AriesFilter filter = new AriesFilter();
		return filter;
	}
	
}
