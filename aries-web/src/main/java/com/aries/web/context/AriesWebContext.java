package com.aries.web.context;

import com.aries.core.io.AriesProperties;

public interface AriesWebContext {
	
	AriesProperties getEnv();
	
	void initEnvironment();
	
}
