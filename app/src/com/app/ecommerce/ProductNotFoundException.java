package com.app.ecommerce;

public class ProductNotFoundException extends RuntimeException{
	
	public ProductNotFoundException(String message)
	{
		super(message);
	}

}
