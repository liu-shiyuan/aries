package com.aries.web.controller.factory;

public interface ControllerFactory {
	<T> T getController(Class<T> clz);
}
