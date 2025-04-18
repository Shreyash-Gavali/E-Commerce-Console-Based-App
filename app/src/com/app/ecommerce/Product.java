package com.app.ecommerce;

import java.util.Comparator;
import java.util.TreeSet;

public class Product implements Comparable<Product> {

	private int productId = 0;
	private String productName = "";
	private String productDescription = "";
	private double productPrice = 0;
	private int productQuantity = 0;
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
	public Product()
	{
		
	}
	public Product(int productId)
	{
		this.productId=productId;
	}
	public Product(int productId, String productName, String productDescription, double productPrice,
			int productQuantity) {
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.productQuantity = productQuantity;
	}

	@Override
	public int compareTo(Product o) {
		
		return this.productName.compareTo(o.productName);
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productDescription="
				+ productDescription + ", productPrice=" + productPrice + ", productQuantity=" + productQuantity + "]";
	}
	
	
	
}
