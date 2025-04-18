package com.app.ecommerce;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {

//	Product product = new Product();
	private int productId = 0;
	private String productName = "";
	private String productDescription = "";
	private double productPrice = 0;
	private int productQuantity = 0;
	private int userId = 0;
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Cart()
	{
		
	}
	public Cart(int productId, String productName, String productDescription, double productPrice, int productQuantity) {
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.productQuantity = productQuantity;
	}
	public HashMap<Integer,ArrayList<Cart>>tempProducts(User user,Product product)
	{
		HashMap<Integer,ArrayList<Cart>> cart = new HashMap<Integer,Cart>();
		cart.put(user.getUserId(),  new Cart(product.getProductId(),product.getProductName(),product.getProductDescription(),product.getProductPrice(),product.getProductQuantity()));
		return cart;
	}
	
}
