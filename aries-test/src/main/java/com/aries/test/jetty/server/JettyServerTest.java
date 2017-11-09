package com.aries.test.jetty.server;

import com.aries.server.jetty.context.JettyContext;
import com.aries.server.jetty.server.JettyServer;
import com.aries.web.server.AriesServer;

public class JettyServerTest {

	// main方法
	public static void main(String[] args) {
		AriesServer server = new JettyServer(new JettyContext());
		server.start();
	}

}
