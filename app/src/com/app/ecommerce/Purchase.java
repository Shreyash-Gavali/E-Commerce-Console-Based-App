package com.app.ecommerce;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public class Purchase {
	static HashMap<Integer,Double> bill = new HashMap<Integer,Double>();
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
	public int getUserTotal() {
		return userTotal;
	}
	public void setUserTotal(int userTotal) {
		this.userTotal = userTotal;
	}
	public double getTotalBill() {
		return totalBill;
	}
	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}
	private int productId = 0;
	private int productQuantity = 0;
	private int userTotal = 0;
	private double totalBill = 0;
	private Timestamp purchaseTime = null;
	
	
	public Timestamp getPurchaseTime() {
		return purchaseTime;
	}
	public void setPurchaseTime(Timestamp purchaseTime) {
		this.purchaseTime = purchaseTime;
	}
	
	public Purchase(int productId, int productQuantity) {
		this.productId = productId;
		this.productQuantity = productQuantity;
	}
	PreparedStatement ps = null;
	Connection con=null;
	ResultSet rs = null;
	private int userId = 0;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Purchase(int productId, double totalBill, Timestamp purchaseTime, int userId) {
		this.productId = productId;
		this.totalBill = totalBill;
		this.purchaseTime = purchaseTime;
		this.userId = userId;
	}
	public Purchase()
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
	public LocalDateTime getCurrentDateTime()
	{
		LocalDateTime ldt = LocalDateTime.now();
		return ldt;
	}
	public void calculateBill(Cart cart,Product products)
	{
		ArrayList<Purchase> productIds = new ArrayList<Purchase>(); 
		int userId = cart.getUserId();
		int productId = cart.getProductId();
		double productPrice = 0;
		try {
			ps=con.prepareStatement("SELECT PRODUCT_ID,QUANTITY FROM CART WHERE USER_ID=?");
			ps.setInt(1, userId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				productIds.add(new Purchase(rs.getInt("PRODUCT_ID"),rs.getInt("QUANTITY")));
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try {
			for(Purchase p : productIds)
			{
				
				try {
					ps=con.prepareStatement("SELECT PRODUCT_PRICE FROM PRODUCTS WHERE PRODUCT_ID=?");
					ps.setInt(1, p.getProductId());
					rs=ps.executeQuery();
					while(rs.next())
					{
						productPrice = productPrice + (rs.getInt("PRODUCT_PRICE")*p.getProductQuantity());
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(" Bill is "+productPrice);
		bill.put(userId,productPrice);
		
	}
	public double purchaseItem(Cart cart,Product products,int userId)
	{
		int id=0;
		int prodId = 0;
		Set<Integer> keys = bill.keySet();
		for(Integer k : keys)
		{
			if(k == userId)
			{
				setTotalBill(bill.get(k));
				id=k;
				try {
					ps=con.prepareStatement("SELECT PRODUCT_ID FROM CART WHERE USER_ID=?");
					ps.setInt(1,id);
					rs=ps.executeQuery();
					while(rs.next())
					{						
						prodId = rs.getInt("PRODUCT_ID");
						try {
							ps=con.prepareStatement("INSERT INTO PURCHASE_HISTORY(USER_ID,PRODUCT_ID,DATE_OF_PURCHASE,TOTAL_PRICE) VALUES(?,?,?,?)");
							ps.setInt(1, userId);
							ps.setInt(2, prodId);
							ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
							ps.setDouble(4,getTotalBill());
							int result = ps.executeUpdate();
							if(result > 0)
							{
								System.out.println(" Purchase Succesfull ");
							}
							else
							{
								System.out.println(" Purchase Unsuccesfull");
							}
						}catch(Exception e)
						{
							e.printStackTrace();
						}
						
					}
						
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
			}
		}
		
		return getTotalBill();
	}
	public void displayAmountToEndUser(int amount)
	{
		setTotalBill(amount);
	}
	public ArrayList<Purchase> checkUserHistory(User user)
	{
		ArrayList<Purchase> pal = new ArrayList<Purchase>();
		ArrayList<Product> prod = new ArrayList<Product>();
		try {
			ps=con.prepareStatement("SELECT * FROM PURCHASE_HISTORY WHERE USER_ID = ?");
			ps.setInt(1,user.getUserId());
			rs=ps.executeQuery();
			while(rs.next())
			{
				pal.add(new Purchase(rs.getInt("PRODUCT_ID"),rs.getDouble("TOTAL_PRICE"),rs.getTimestamp("DATE_OF_PURCHASE"),rs.getInt("USER_ID")));
				try {
					ps=con.prepareStatement("SELECT * FROM PRODUCTS WHERE PRODUCT_ID=?");
					ps.setInt(1,rs.getInt("PRODUCT_ID"));
					rs=ps.executeQuery();
					while(rs.next())
					{
						//public Product(int productId, String productName, String productDescription, double productPrice,
						//int productQuantity
						prod.add(new Product(rs.getInt("PRODUCT_ID"),rs.getString("PRODUCT_NAME"),rs.getString("PRODUCT_DESCRIPTION"),rs.getDouble("PRODUCT_PRICE"),rs.getInt("PRODUCT_QUANTITY")));
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		prod.stream().forEach((s)->System.out.println(s));
		return pal;
	}
}
