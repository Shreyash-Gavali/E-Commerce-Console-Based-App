package com.app.ecommerce;

import com.app.ecommerce.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeSet;
public class E_Commerce_Implementation {

	public static ArrayList<String> eCommerceMenu()
	{
		ArrayList<String> a = new ArrayList<String>();
		a.add("Welcome to E - Commerce Based Application");
		a.add(" User Operation ");
		a.add(" 1. User Registration ");
		a.add(" 2. User Login ");
		a.add(" 3. User View Product item as Sorted Order ");
		a.add(" 4. Buy Product ");
		a.add(" 5. View Cart ");
		a.add(" 6. Purchase the Item ");
		a.add(" Admin Operation ");
		a.add(" 7. Add Product Item ");
		a.add(" 8. Calculate Bill ");
		a.add(" 9. Display Amount to End User ");
		a.add(" 10. Check Quantity ");
		a.add(" 11. Check Registered ");
		a.add(" 12. Check The Particular User History ");
		a.add(" Guest Operation ");
		a.add(" 13. View Product Item ");
		a.add(" 14. Not Purchase Item ");
		a.add(" 15. Continue  ");
		a.add(" 16. Exit App ");
		return a;
	}
	public static void main(String[] args) {
		
		String continueApp = "T";
		int userChoice = 0;
		User user = new User();
		Admin a = new Admin();
		String role = user.getRole();
		Scanner sc = new Scanner(System.in);
		UserOperation uo = new UserOperation();
		AdminOperation ao = new AdminOperation();
		int currentUserId = 0;
		 int currentAdminId = 0;
			while(continueApp.equalsIgnoreCase("T"))
			{
				ArrayList<String> al =eCommerceMenu();
				for(String s :al)
				{
					System.out.println(s);
				}
				userChoice = sc.nextInt();
				switch(userChoice)
				{
				case 1: 
					uo.registerUser();
					break;
				case 2: 
					Optional<User> opt=uo.loginUser();
					if(opt.isPresent())
					{
						user=opt.get();						
						role = user.getRole();
						System.out.println("role "+role);
						if(role.equalsIgnoreCase("USER"))
						{							
							currentUserId = user.getUserId();
//							System.out.println(currentUserId);
						}
						else if(role.equalsIgnoreCase("ADMIN"))
						{
							currentAdminId = user.getUserId();
							a.setUserId(currentUserId);
//							System.out.println(currentAdminId);
						}	
					}
					else
					{
						System.out.println("  Login Failed ❌ ");
					}
					
					break;
				case 3: 
					TreeSet<Product> products= uo.viewProducts();
					for(Product p : products)
					{
						System.out.println("Product Id ➮ "+p.getProductId());
						System.out.println("Product Name ➮ "+p.getProductName());
						System.out.println("Product Description ➮ "+p.getProductDescription());
						System.out.println("Available Quantity  ➮ "+p.getProductQuantity());
						System.out.println("Price ➮ "+p.getProductPrice());
						System.out.println("﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎");
					}
					break;
				case 4: 
					Product p = new Product();
					Cart c1 = new Cart();
					System.out.println(" Enter the Product ID  to buy product ⏎ ");
					int productId = sc.nextInt();
					p.setProductId(productId);
					System.out.println(" Enter the quantity ⏎ ");
					int quantity = sc.nextInt();
					p.setProductQuantity(quantity);
					uo.buyProduct(user,p);
					System.out.println(" Do You Want to View The Cart Yes  / No");
					String viewCart = sc.next();
					if(viewCart.equalsIgnoreCase("YES"))
					{
						Product userProd = new Product();
						ArrayList<Cart> newCart = c1.viewCartItems(user, userProd);
						newCart.stream().forEach((s)->{
							System.out.println(" USER_ID ➮ "+s.getUserId());
							System.out.println(" PRODUCT_ID ➮ "+s.getProductId());
							System.out.println(" PRODUCT_QUANTITY ➮ "+s.getProductQuantity());
							System.out.println("﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎");
						});
					}
					break;
				case 5: 
					Cart c = new Cart();
					Product userProd = new Product();
				ArrayList<Cart> newCart = c.viewCartItems(user, userProd);
				newCart.stream().forEach((s)->{
					System.out.println(" USER_ID ➮ "+s.getUserId());
					System.out.println(" PRODUCT_ID ➮ "+s.getProductId());
					System.out.println(" PRODUCT_QUANTITY  ➮ "+s.getProductQuantity());
					System.out.println("﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎");
				});
				
				break;
				case 6: 
					System.out.println(" Enter Username ");
					int userName = currentUserId;
					Purchase purchase = new Purchase();
					Product prod1 = new Product();
					Cart c3 = new Cart();
					double price = purchase.purchaseItem(c3,prod1,userName);
					System.out.println(" User Id "+userName);
					System.out.println(" "+price);
					break;
				case 7: 
					ao.addProduct(currentAdminId);
					break;
				case 8: 
					Cart c2 = new Cart();
//					c2.setUserId(currentUserPurchase);
					Product purprod = new Product();
					ao.calculateUserBill(c2, purprod,currentAdminId,currentUserId);
					break;
				case 9: double amount = 0;
						System.out.println("Display The Amount to End User");
						amount=sc.nextDouble();
						ao.displayAmountToEndUser(amount);
					break;
				case 10: 
					int productQuantity = ao.getQuantity(currentAdminId);
					System.out.println(" Quantity is ➮ "+productQuantity);
					break;
				case 11: 
					ArrayList<User> users =ao.checkRegisteredUser(currentAdminId);
					for(User u : users)
					{
						System.out.println(" User ID ➮ "+u.getUserId());
						System.out.println(" UserName ➮ "+u.getUserName());
						System.out.println(" First Name ➮ "+u.getUserFirstName());
						System.out.println(" Last Name ➮ "+u.getUserLastName());
						System.out.println(" Email Id ➮ "+u.getUserEmailId());
						System.out.println(" Mobile ➮ "+u.getUserMobileNumber());
						System.out.println(" City ➮ "+u.getUserCity());
						System.out.println("﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎");
					}
					break;
				case 12: 
					System.out.println(" Enter User Name ");
					String name = sc.next();
					user.setUserName(name);
					ao.checkUserHistory(user);
					break;
				case 13: 
					TreeSet<Product> pp= uo.viewProducts();
					for(Product prod : pp)
					{
						
						System.out.println("Product Id ➮ "+prod.getProductId());
						System.out.println("Product Name ➮ "+prod.getProductName());
						System.out.println("Product Description ➮ "+prod.getProductDescription());
						System.out.println("Available Quantity  ➮ "+prod.getProductQuantity());
						System.out.println("Price ➮ "+prod.getProductPrice());
						System.out.println("﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎﹎");
					}
					break;
				case 14:
						throw new UserNotFoundException(" Login or Regitser to Buy Products ❗");
//					break;
				case 15: 
					continueApp = "T";
					System.out.println(" ------------------------------------ ");
					break;
				case 16:
					System.out.println("Exiting App ");
					continueApp = "F";
					DatabaseConnection dc = new DatabaseConnection();
					 Optional<Connection> conopt = dc.getDatabaseConnection();
					 Connection con;
					if(conopt.isEmpty())
					 {		 
						 con=conopt.get();
						 try {
							 con.close();							 
						 }
						 catch(SQLException se)
						 {
							 se.printStackTrace();
						 }
					 }
					else
					{
						System.out.println("Cannot Establish Database Connection ");
					}
					break;
				default:
						throw new InvalidInputEnteredException(" Please Enter a Number Specified In the Menu ❗ ");
				}
			}
		

		
		
	}

}
