package com.aries.web.context;

import com.aries.core.io.AriesEnv;
import com.aries.core.io.AriesProperties;

public abstract class AbstractAriesWebContext implements AriesWebContext {

	private AriesProperties env;
	
	protected AbstractAriesWebContext() {
		initSettings();
	}
	
	@Override
	public AriesProperties getEnv() {
		return env;
	}
	
	@Override
	public void initSettings() {
		env = new AriesEnv();
	}
	
}
