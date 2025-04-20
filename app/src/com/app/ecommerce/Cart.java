package com.app.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class Cart {

	private int userId = 0;
	private int productId=0;
	private int productQuantity = 0;
	
	private Connection con;
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	PreparedStatement ps = null;
	ResultSet rs = null;
	Product p = new Product();
	Product product = new Product();
	User user = new User();
	public Cart()
	{
		DatabaseConnection dc = new DatabaseConnection();
		 Optional<Connection> conopt = dc.getDatabaseConnection();
		if(conopt.isPresent())
		 {		 
			  this.con = conopt.get();
		 }
		else
		{
			System.out.println("Cannot Establish Database Connection ");
		}
	}
	
	public Cart(User user, Product products) {

		this.userId = user.getUserId();
		this.productId = products.getProductId();
		this.productQuantity = products.getProductQuantity();
	}
	
	public Cart(int userId, int productId, int productQuantity) {
		this.userId = userId;
		this.productId = productId;
		this.productQuantity = productQuantity;
	}

	public ArrayList<Cart> viewCartItems(User user1,Product products1)
	{ 
		Cart c = null;
		ArrayList<Cart> alCart = new ArrayList<Cart>();
		try {
			ps=con.prepareStatement("SELECT * FROM CART WHERE USER_ID = ? ");
			ps.setInt(1, user1.getUserId());
			rs=ps.executeQuery();
			while(rs.next())
			{
				int userId = rs.getInt("USER_ID");
				int productId = rs.getInt("PRODUCT_ID");
				int quantity = rs.getInt("QUANTITY");
				c=new Cart();
				c.setUserId(userId);
				c.setProductId(productId);
				c.setProductQuantity(quantity);
				alCart.add(new Cart(c.getUserId(),c.getProductId(),c.getProductQuantity()));
			}
			if(c==null)
			{
				System.out.println(" Cannot Find Items ");
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return alCart;
	}
	public boolean doesProductExistInCart(User user1,Product products1)
	{
		boolean productExist = false;
		Cart c = null;
		try {
			ps=con.prepareStatement("SELECT * FROM CART WHERE USER_ID = ? AND PRODUCT_ID = ? ");
			ps.setInt(1, user1.getUserId());
			ps.setInt(2, products1.getProductId());
			rs=ps.executeQuery();
			productExist = rs.next();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return productExist;
	}
	public int addCartItems(User user1,Product products1, int currentUserId)
	{
		int result = 0;
		Cart cart = new Cart();
		
		if(cart.doesProductExistInCart(user1,products1)==true)
		{
			updateCart(user1,products1);
		}
		else
		{	
		try {
			
			ps=con.prepareStatement("INSERT INTO CART(USER_ID,PRODUCT_ID,QUANTITY) VALUES(?,?,?)");
			ps.setInt(1,user1.getUserId());
			ps.setInt(2,products1.getProductId());
			ps.setInt(3, products1.getProductQuantity());
			result = ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
		return result;
	}
	public void updateCart(User user1,Product products)
	{
		int currentCartQuantity = 0;
		try {
			ps=con.prepareStatement("SELECT QUANTITY FROM CART WHERE USER_ID=? AND PRODUCT_ID=?");
			ps.setInt(1, user1.getUserId());
			ps.setInt(2, products.getProductId());
			rs=ps.executeQuery();
			if(rs.next())
			{
				currentCartQuantity = rs.getInt("QUANTITY");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try {
			ps=con.prepareStatement("UPDATE CART SET QUANTITY=? WHERE PRODUCT_ID =? AND USER_ID=?");
			ps.setInt(1, currentCartQuantity+products.getProductQuantity());
			ps.setInt(2, products.getProductId());
			ps.setInt(3, user1.getUserId());
			int res = ps.executeUpdate();
			if(res>0)
			{
				System.out.println(" Product "+products.getProductName()+" Quantity Succesfully Updated To Cart ✔ ");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
}
