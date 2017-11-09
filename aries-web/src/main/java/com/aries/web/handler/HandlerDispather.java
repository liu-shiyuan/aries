package com.aries.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aries.core.config.Config;

public interface HandlerDispather {

	void doDispath(HttpServletRequest req, HttpServletResponse res);

	void init(Config config);

}
