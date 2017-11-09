package com.arie.test.jetty.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aries.web.constant.HttpMethod;
import com.aries.web.controller.AbstractController;

public class TestController extends AbstractController {

	@Override
	public Object handleRequest(HttpServletRequest req, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        try (PrintWriter out = response.getWriter()) {
        	out.println("<h1>" + "new Project to Route here to say Hello World!!" + "</h1>");
        } catch (Exception e) {
        }
        
        return null;
	}

	@Override
	public HttpMethod supportedMethod() {
		return HttpMethod.GET;
	}

	@Override
	public String mappingPath() {
		return "/test";
	}

}
