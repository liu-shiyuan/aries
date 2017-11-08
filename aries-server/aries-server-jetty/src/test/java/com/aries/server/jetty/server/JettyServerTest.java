package com.aries.server.jetty.server;

import com.aries.server.jetty.context.JettyContext;
import com.aries.web.server.AriesServer;

public class JettyServerTest {

	public static void main(String[] args) {
		AriesServer server = new JettyServer(new JettyContext());
		server.start();
	}

}
