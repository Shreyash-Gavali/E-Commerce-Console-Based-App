package com.app.ecommerce;

public class NotAAdminException extends RuntimeException {

	public NotAAdminException(String message) {
		super(message);
	}
}
