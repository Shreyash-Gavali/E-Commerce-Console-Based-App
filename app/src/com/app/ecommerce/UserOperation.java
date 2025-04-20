package com.app.ecommerce;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.IntStream;

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
//	public int randomUserIdGenerator()
//	{
//			Random r = new Random();
//			int userId = 0;
//			IntStream i = r.ints();
//			userId =i.filter((e)->e>0).limit(1).findFirst().getAsInt();			
//			try {
//				ps=con.prepareStatement("SELECT USER_ID FROM USERS WHERE USER_ID=?");
//				ps.setInt(1, userId);
//				rs=ps.executeQuery();
//				if(rs.next())
//				{
//					return 0;
//				}
//				
//			}catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		return userId;
//	}
	public UserOperation()
	{
		DatabaseConnection dc = new DatabaseConnection();
		 Optional<Connection> conopt = dc.getDatabaseConnection();
		if(conopt.isPresent())
		 {		 
			  con = conopt.get();
		 }
		else
		{
			System.out.println("Cannot Establish Database Connection ");
		}
	} 
	public void registerUser()
	{
		try {
			
		System.out.println(" Enter User ID ⏎ ");
		int userId = sc.nextInt();
		System.out.println(" Enter User First Name ⏎ ");
		String userFirstName = sc.next();
		System.out.println(" Enter User Last Name ⏎ ");
		String userLastName = sc.next();
		System.out.println(" Enter User User Name ⏎ ");
		String userName = sc.next();		
		System.out.println(" Enter User Password ⏎ ");
		String userPassword = sc.next();		
		System.out.println(" Enter User City ⏎ ");
		String userCity = sc.next();		
		System.out.println(" Enter User Email Id ⏎ ");
		String userEmailId = sc.next();		
		System.out.println(" Enter User Mobile Number ⏎ ");
		long userMobileNumber = sc.nextLong();
		String role = "USER";
		User u = new User(userId,userFirstName,userLastName,userName,userPassword,userCity,userEmailId,userMobileNumber,role);
		ps=con.prepareStatement("INSERT INTO USERS(USER_ID,USER_FIRST_NAME,USER_LAST_NAME,USER_USERNAME,USER_PASSWORD,USER_CITY,USER_EMAIL_ID,USER_MOBILE_NUMBER,ROLES) VALUES(?,?,?,?,?,?,?,?,?)");
		ps.setInt(1,u.getUserId());
		ps.setString(2,u.getUserFirstName());
		ps.setString(3,u.getUserLastName());
		ps.setString(4,u.getUserName());
		ps.setString(5,u.getUserPassword());
		ps.setString(6,u.getUserCity());
		ps.setString(7,u.getUserEmailId());
		ps.setLong(8, u.getUserMobileNumber());
		ps.setString(9, u.getRole());
		int queryResult = ps.executeUpdate();
		if(queryResult > 0)
		{
			System.out.println(" User ➮ "+u.getUserFirstName()+" "+u.getUserLastName()+"  Registered Succesfully ✔️");
		}
		else
		{
			System.out.println(" Unable to Register User  ❌ "+u.getUserFirstName()+" "+u.getUserLastName()+" ");
		}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		
	}
	public Optional<User> loginUser()
	{
		Optional<User> opt = null;
		int userId = 0;
		System.out.println(" Enter User Name ⏎  ");
		String userName = sc.next();
		user.setUserName(userName);
		System.out.println(" Enter User Password ⏎  ");
		String userPass = sc.next();
		user.setUserPassword(userPass);
		try
		{			
			ps=con.prepareStatement("SELECT USER_FIRST_NAME,USER_LAST_NAME,USER_ID,ROLES FROM USERS WHERE USER_USERNAME=? AND USER_PASSWORD=?");
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserPassword());
			rs=ps.executeQuery();
			if(rs.next())
			{
				System.out.println(" Welcome ✔️ "+rs.getString("USER_FIRST_NAME")+" "+rs.getString("USER_LAST_NAME"));
				user.setUserId(rs.getInt("USER_ID"));
				user.setRole(rs.getString("ROLES"));
				if(rs.getString("ROLES").equalsIgnoreCase("USER") || rs.getString("ROLES").equalsIgnoreCase("ADMIN"))
				{
					opt =Optional.ofNullable(user);
				}
				else
				{
					throw new UserNotFoundException("Not A User ❌ "+user.getUserName());
				}
				return opt;
			}
			else
			{
				throw new UserNotFoundException(" Cannot Find ❌ "+user.getUserName());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return Optional.empty();
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
	public void buyProduct(User user1, Product products)
	{
		int currentUserId = user1.getUserId();
		int productId = products.getProductId();
		int quantity = products.getProductQuantity();
		try
		{			
			ps=con.prepareStatement("SELECT * FROM PRODUCTS WHERE PRODUCT_ID = ?");
			ps.setInt(1,productId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				int getProductId=rs.getInt("PRODUCT_ID");
				String getProductName = rs.getString("PRODUCT_NAME");
				String getProductDescription = rs.getString("PRODUCT_DESCRIPTION");
				double getProductPrice = rs.getDouble("PRODUCT_PRICE");
				int getProductQuantity = rs.getInt("PRODUCT_QUANTITY");
			
				if(quantity > getProductQuantity)
				{
					System.out.println("Entered Quantity is not Available ❌");
				}
				else
				{
					int result = cart.addCartItems(user1, products,currentUserId);
					if(result > 0)
					{
						System.out.println(" Product "+products.getProductName()+" Added Successfull To Cart ✔ ");
					}
					try {	
						int newQuantity = getProductQuantity-quantity;
						ps=con.prepareStatement("UPDATE PRODUCTS SET PRODUCT_QUANTITY=? WHERE PRODUCT_ID=?");
						ps.setInt(1,newQuantity);
						ps.setInt(2,products.getProductId());
						ps.executeUpdate();
						
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}		
			} 
			else
			{
				throw new ProductNotFoundException(" Cannot Find Product ❌ \n Enter Correct Id ");
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
}
