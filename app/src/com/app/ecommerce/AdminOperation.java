package com.app.ecommerce;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class AdminOperation {

	private Connection con;
	PreparedStatement ps = null;
	ResultSet rs = null;
	CallableStatement cs = null;
	Scanner sc = new Scanner(System.in);
	User user = new User();
	Cart cart = new Cart();
	public AdminOperation()
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
	public void addProduct()
	{
		try {
			System.out.println("Product ID >> ");
			int productId = sc.nextInt();
			System.out.println("Product Name >> ");
			String productName = sc.next();
			System.out.println("Product Description >> ");
			String productDescription = sc.next();
			System.out.println("Product Quantity >> ");
			int productQuantity = sc.nextInt();
			System.out.println("Product Price >> ");
			double productPrice = sc.nextDouble(); 
			Product product = new Product(productId,productName,productDescription,productPrice,productQuantity); 
			ps=con.prepareStatement("INSERT INTO PRODUCTS(PRODUCT_ID,PRODUCT_NAME,PRODUCT_DESCRIPTION,PRODUCT_PRICE,PRODUCT_QUANTITY) VALUES(?,?,?,?,?)");
			ps.setInt(1,product.getProductId());
			ps.setString(2,product.getProductName());
			ps.setString(3,product.getProductDescription());
			ps.setDouble(4,product.getProductPrice());
			ps.setInt(4, product.getProductQuantity());
			int queryRes = ps.executeUpdate();
			if(queryRes > 0)
			{
				System.out.println(" Insertion Succesfull ");
			}
			else
			{
				System.out.println(" Insertion Unsuccesfull ");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public int getQuantity()
	{
		System.out.println(" Enter Product Id >> ");
		int productId = sc.nextInt();
		int quantity = 0;
		Product product = new Product(productId);
		try {
			ps=con.prepareStatement("SELECT PRODUCT_QUANTITY FROM PRODUCTS WHERE PRODUCT_ID = ?");
			ps.setInt(1,product.getProductId());
			rs=ps.executeQuery();
			while(rs.next())
			{
				quantity = rs.getInt("PRODUCT_QUANTITY");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return quantity;
		
	}
	public ArrayList<User> checkRegisteredUser()
	{
//		User user = new User();
		ArrayList<User> user=new ArrayList<User>();
		try {
			ps=con.prepareStatement("SELECT * FROM USERS");
			rs = ps.executeQuery();
			while(rs.next())
			{
				int userId = rs.getInt("USER_ID");
				String userFirstName = rs.getString("USER_FIRST_NAME");
				String userLastName = rs.getString("USER_LAST_NAME");
				String userName = rs.getString("USER_USERNAME");
				String userPassword = rs.getString("USER_PASSWORD");
				String userCity = rs.getString("USER_CITY");
				String userEmailId  = rs.getString("USER_EMAIL_ID");
				long userMobileNumber = rs.getLong("USER_MOBILE_NUMBER");
				user.add(new User(userId,userFirstName,userLastName,userName,userPassword,userCity,userEmailId,userMobileNumber));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return user;
	}
}
