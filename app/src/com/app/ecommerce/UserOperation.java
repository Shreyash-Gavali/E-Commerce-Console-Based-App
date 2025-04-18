package com.app.ecommerce;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeSet;

public class UserOperation  {

	private Connection con;
	PreparedStatement ps = null;
	ResultSet rs = null;
	CallableStatement cs = null;
	Scanner sc = new Scanner(System.in);
	User user = new User();
	Cart cart = new Cart();
//	ArrayList<String>
	Product p = new Product();
	
	public UserOperation()
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
	public void registerUser()
	{
		try {
			
		System.out.println(" Enter User ID");
		int userId = sc.nextInt();
		System.out.println(" Enter User First Name");
		String userFirstName = sc.next();
		System.out.println(" Enter User Last Name");
		String userLastName = sc.next();
		System.out.println(" Enter User User Name");
		String userName = sc.next();		
		System.out.println(" Enter User Password ");
		String userPassword = sc.next();		
		System.out.println(" Enter User City ");
		String userCity = sc.next();		
		System.out.println(" Enter User Email Id ");
		String userEmailId = sc.next();		
		System.out.println(" Enter User Mobile Number");
		long userMobileNumber = sc.nextLong();
		User u = new User(userId,userFirstName,userLastName,userName,userPassword,userCity,userEmailId,userMobileNumber);
		ps=con.prepareStatement("INSERT INTO USERS(USER_ID,USER_FIRST_NAME,USER_LAST_NAME,USER_USERNAME,USER_PASSWORD,USER_CITY,USER_EMAIL_ID,USER_MOBILE_NUMBER) VALUES(?,?,?,?,?,?,?,?)");
		ps.setInt(1,u.getUserId());
		ps.setString(2,u.getUserFirstName());
		ps.setString(3,u.getUserLastName());
		ps.setString(4,u.getUserName());
		ps.setString(5,u.getUserPassword());
		ps.setString(6,u.getUserCity());
		ps.setString(7,u.getUserEmailId());
		ps.setLong(8, u.getUserMobileNumber());
		int queryResult = ps.executeUpdate();
		if(queryResult > 0)
		{
			System.out.println(" User "+u.getUserFirstName()+" "+u.getUserLastName()+" Registered Succesfully ");
		}
		else
		{
			System.out.println(" Unable to Register User ");
		}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		
	}
	public void loginUser()
	{
		
		System.out.println(" Enter User Name :- ");
		String userName = sc.next();
		user.setUserName(userName);
		System.out.println(" Enter User Password :- ");
		String userPass = sc.next();
		user.setUserPassword(userPass);
		try
		{			
			ps=con.prepareStatement("SELECT USER_FIRST_NAME,USER_LAST_NAME FROM USERS WHERE USER_USERNAME=? AND USER_PASSWORD=?");
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserPassword());
			rs=ps.executeQuery();
			if(rs.next())
			{
				System.out.println(" Welcome  "+rs.getString("USER_FIRST_NAME")+" "+rs.getString("USER_LAST_NAME"));
			}
			else
			{
				throw new UserNotFoundException(" Cannot Find  "+user.getUserName());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	public TreeSet<Product> viewProducts()
	{
		TreeSet<Product> products = new TreeSet<Product>();
		try
		{			
			ps=con.prepareStatement("SELECT * FROM PRODUCTS");
			rs=ps.executeQuery();
			while(rs.next())
			{
				int productId = rs.getInt("PRODUCT_ID");
				String productName = rs.getString("PRODUCT_NAME");
				String productDescription = rs.getString("PRODUCT_DESCRIPTION");
				double productPrice = rs.getInt("PRODUCT_PRICE"); 
				int productQuantity = rs.getInt("PRODUCT_QUANTITY");
				products.add(new Product(productId,productName,productDescription,productPrice,productQuantity));
			} 
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return products;
	}
	public void buyProduct(int productId, int quantity)
	{
		
		try
		{			
			ps=con.prepareStatement("SELECT * FROM PRODUCTS WHERE PRODUCT_ID = ?");
			ps.setInt(1,productId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				int getProductId=rs.getInt("PRODUCT_ID");
				String getProductName = rs.getString("PRODUCT_NAME");
				String getProductDescription = rs.getString("PRODUCT_DESCRIPTION");
				double getProductPrice = rs.getDouble("PRODUCT_PRICE");
				int getProductQuantity = rs.getInt("PRODUCT_QUANTITY");
			
				if(quantity > getProductQuantity)
				{
					System.out.println("Entered Quantity is not Available");
				}
				
				Cart c = new Cart();
//				HashMap<Integer,Cart> cart= c.tempProducts(user,new Product(getProductId,getProductName,getProductDescription,getProductPrice,getProductQuantity));
				
			} 
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	public void addToCart()
	{
		
	}
}
