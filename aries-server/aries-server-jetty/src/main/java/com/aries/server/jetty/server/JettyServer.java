package com.aries.server.jetty.server;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.aries.web.context.AriesContextListener;
import com.aries.web.context.AriesWebContext;
import com.aries.web.server.AbstractAriesServer;

public class JettyServer extends AbstractAriesServer<JettySettings> {
	
	public JettyServer(AriesWebContext context) {
		super(context);
	}

	private Server server;

	@SuppressWarnings("resource")
	@Override
	public void start() {
		server = new Server();
		ServerConnector[] connectors = new ServerConnector[1];

		ServerConnector serverConnector = new ServerConnector(server);
		serverConnector.setIdleTimeout(TimeUnit.MINUTES.toMillis(10));
		serverConnector.setSoLingerTime(-1);
		serverConnector.setHost(getSettings().getHost());
		serverConnector.setPort(getSettings().getPort());
		
		connectors[0] = serverConnector;
		server.setConnectors(connectors);
		
		// add filter
		Filter f = getAriesFilter();
		FilterHolder filterHolder = new FilterHolder(f);
		ServletContextHandler handler = new ServletContextHandler();
		// ������Ե���˼���ɸù������������Դ��ͨ��ʲô���ķ�ʽ���ʵ��ģ�����������ת��������ʽ���󡢰����ȣ�Ĭ��ΪREQUEST
		EnumSet<DispatcherType> dispatches = EnumSet.of(DispatcherType.REQUEST);
		handler.addFilter(filterHolder, "/*", dispatches);
		
		handler.addEventListener(new AriesContextListener(getContext()));
		
		server.setHandler(handler);
		
		
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
