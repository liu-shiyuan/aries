package com.aries.test.jetty.controller;

import com.aries.web.annotation.Controller;
import com.aries.web.annotation.GET;
import com.aries.web.annotation.Path;

@Controller
@Path("/cc/")
public class TestAController {

	@GET("dd")
	public Object x() {
		return "Nothing More.";
	}
	
	
	public static void main(String[] args) {
		Path[] ps = TestAController.class.getAnnotationsByType(Path.class);
		System.out.println(ps.length);
		System.out.println(ps[0].value()[0]);
	}
}
