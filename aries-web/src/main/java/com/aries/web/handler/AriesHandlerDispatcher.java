package com.aries.web.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aries.core.config.Config;
import com.aries.web.controller.factory.CachedControllerFactory;
import com.aries.web.controller.mapping.MappingResult;
import com.google.gson.Gson;

public class AriesHandlerDispatcher implements HandlerDispather {
	
	private RequestMappingRegistry registry;
	

	@Override
	public void doDispath(HttpServletRequest req, HttpServletResponse res) {
		try {
			MappingResult result = registry.getMappingResult(req, res);
			Object o = result.simpleInvoke();
			render(o, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(Config config) {
		String[] dirs = config.getHandlerPackages();
		registry = getRequestMappingRegistry();
		registry.register(dirs);
	}
	
	public RequestMappingRegistry getRequestMappingRegistry() {
		return new RequestMappingRegistry(new CachedControllerFactory());
	}
	
	private void render(Object result, HttpServletResponse res) {
		if (Objects.isNull(result))
			return;
		try (OutputStream os = res.getOutputStream()) {
			os.write(new Gson().toJson(result).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
