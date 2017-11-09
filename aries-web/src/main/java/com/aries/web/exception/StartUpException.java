package com.aries.web.exception;

public class StartUpException extends RuntimeException {

	public StartUpException() {
	}
	
	public StartUpException(String msg) {
		super(msg);
	}
	
	public StartUpException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	private static final long serialVersionUID = 1861946437710471675L;
	
}
