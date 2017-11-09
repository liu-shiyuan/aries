package com.aries.web.handler;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.reflections.Reflections;

import com.aries.core.config.Config;
import com.aries.web.controller.AbstractController;
import com.google.common.collect.Lists;

public class AriesHandlerDispatcher implements HandlerDispather {
	
	private List<AbstractController> list;

	@Override
	public void doDispath(HttpServletRequest req, HttpServletResponse res) {
		try {
			list.get(0).handleRequest(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(Config config) {
		String[] dirs = config.getHandlerPackages();
		getPathClasses(dirs[0]);
	}
	
	private void getPathClasses(String path) {
		Reflections refs = new Reflections(path);
		Set<Class<? extends AbstractController>> set = refs.getSubTypesOf(AbstractController.class);
		list = Lists.newArrayList();
		for (Class<? extends AbstractController> clz : set) {
			try {
				list.add(clz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
//		String x = AriesHandlerDispatcher.class.getPackage().getName();
		String x = "com.aries.web.handler";
		Reflections refs = new Reflections(x);
		Set<Class<? extends AbstractController>> list = refs.getSubTypesOf(AbstractController.class);
		list.forEach(System.out::println);
		
	}

}
