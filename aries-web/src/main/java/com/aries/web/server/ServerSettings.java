package com.aries.web.server;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.aries.core.io.AriesProperties;
import com.aries.web.context.AriesWebContext;

public class ServerSettings {
	
	private static final String SERVER_PORT = "server_port";
	private static final String SERVER_HOST = "server_host";
	private static final int SERVER_DEFAULT_PORT = 8080;
	private static final String SERVER_DEFAULT_HOST = "localhost";
	
	private String host = SERVER_DEFAULT_HOST;
    private int port = SERVER_DEFAULT_PORT;
    
    public ServerSettings(AriesWebContext context) {
    	AriesProperties env = context.getEnv();
    	String host = env.getProperty(SERVER_HOST);
    	if (StringUtils.isNotEmpty(host))
    		this.host = host;
    	Integer port = env.getProperty(SERVER_PORT, Integer.class);
    	if (Objects.nonNull(port))
    		this.port = port;
    }

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	

}
