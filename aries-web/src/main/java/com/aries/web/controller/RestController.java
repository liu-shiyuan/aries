package com.aries.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RestController {

	Object handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception;
	
}
