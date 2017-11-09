package com.aries.test.jetty.server;

import com.aries.core.config.Config;

public class AppConfig extends Config {

	@Override
	public String[] getHandlerPackages() {
		return new String[] {
				"com.aries.test.jetty.controller"
		};
	}

}
