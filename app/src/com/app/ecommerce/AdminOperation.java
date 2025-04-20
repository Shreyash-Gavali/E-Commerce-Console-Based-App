package com.app.ecommerce;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class AdminOperation {

	private Connection con;
	PreparedStatement ps = null;
	ResultSet rs = null;
	CallableStatement cs = null;
	Purchase purchase = new Purchase();
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
			System.out.println("Cannot Establish Database Connection ❌");
		}
	}
	public boolean checkIfAdmin(int currentUserId)
	{
		String role = "";
		boolean isAdmin = true;
		try {
			ps=con.prepareStatement("SELECT ROLES FROM USERS WHERE USER_ID=?");
			ps.setInt(1, currentUserId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				role = rs.getString("ROLES").trim();
				if(role.equalsIgnoreCase("ADMIN"))
				{
					isAdmin = true;
				}
				else
				{
					isAdmin = false;
				}
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return isAdmin;
	}
	public void addProduct(int currentUserId)
	{
		
		boolean isAdmin = checkIfAdmin(currentUserId);
		if(isAdmin == true)
		{
			try {
				System.out.println("Product ID ⏎ ");
				int productId = sc.nextInt();
				System.out.println("Product Name ⏎ ");
				String productName = sc.next();
				System.out.println("Product Description ⏎ ");
				String productDescription = sc.nextLine();
				System.out.println("Product Quantity ⏎ ");
				int productQuantity = sc.nextInt();
				System.out.println("Product Price ⏎ ");
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
					System.out.println(" Insertion Succesfull ✔️ ");
				}
				else
				{
					System.out.println(" Insertion Unsuccesfull ❌ ");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		else
		{
			throw new NotAAdminException(" ⚠ Permission Restricted: You must be an administrator to use this feature. ");
		}
	}
	public int getQuantity(int currentUserId)
	{
		boolean isAdmin = checkIfAdmin(currentUserId);
		if(isAdmin == true)
		{
			System.out.println(" Enter Product Id ⏎  ");
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
		else
		{
			throw new NotAAdminException(" ⚠ Permission Restricted: You must be an administrator to use this feature. ");
		}
	}
	public ArrayList<User> checkRegisteredUser(int currentUserId)
	{
		boolean isAdmin = checkIfAdmin(currentUserId);
		if(isAdmin == true)
		{
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
					String role = rs.getString("ROLES");
					user.add(new User(userId,userFirstName,userLastName,userName,userPassword,userCity,userEmailId,userMobileNumber,role));
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return user;
		}
		else
		{
			throw new NotAAdminException(" ⚠ Permission Restricted: You must be an administrator to use this feature. ");
		}
	}
	public void calculateUserBill(Cart cart,Product products,int currentAdminId,int currentUserId)
	{
		boolean isAdmin = checkIfAdmin(currentAdminId);
		if(isAdmin == true)
		{
			cart.setUserId(currentUserId);
			purchase.calculateBill(cart, products);
		}
		else
		{
			throw new NotAAdminException(" ⚠ Permission Restricted: You must be an administrator to use this feature. ");
		}
	}
	public void checkUserHistory(User user)
	{
		try {
			ps=con.prepareStatement("SELECT USER_ID FROM USERS WHERE USER_USERNAME=?");
			ps.setString(1, user.getUserName());
			rs=ps.executeQuery();
			if(rs.next())
			{
				user.setUserId(rs.getInt("USER_ID"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		ArrayList<Purchase> pl = purchase.checkUserHistory(user);
		pl.stream().forEach((s)->{
			System.out.println(" User Id "+s.getUserId());
			System.out.println(" Product Id "+s.getProductId());
			System.out.println(" Date Of Purchase  "+s.getPurchaseTime());
			System.out.println(" Total Price "+s.getTotalBill());
			
			
		});
	}
	public void displayAmountToEndUser(double amount)
	{
		
	}
}

