package com.iss.base.common;

/**
 * The service (business) is unusually "an account or password error", which
 * only does the INFO level logging
 * 
 * @see WebMvcConfigurer
 */
@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {
	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
