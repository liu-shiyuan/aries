package com.aries.server.jetty.server;

import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

import com.aries.server.jetty.handler.HelloHandler;
import com.aries.web.context.AriesWebContext;
import com.aries.web.server.AbstractAriesServer;

public class JettyServer extends AbstractAriesServer<JettySettings> {
	
	public JettyServer(AriesWebContext context) {
		super(context);
	}

	private Server server;

	@Override
	public void start() {
		server = new Server();
		ServerConnector[] connectors = new ServerConnector[1];

		ServerConnector serverConnector = new ServerConnector(server);
		serverConnector.setIdleTimeout(TimeUnit.HOURS.toMillis(1));
		serverConnector.setSoLingerTime(-1);
		serverConnector.setHost(getSettings().getHost());
		serverConnector.setPort(getSettings().getPort());
		
		connectors[0] = serverConnector;
		server.setConnectors(connectors);
		
		server.setHandler(new HelloHandler());
		
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void stop() {
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected JettySettings initSettings() {
		return new JettySettings(getContext());
	}
	
}
