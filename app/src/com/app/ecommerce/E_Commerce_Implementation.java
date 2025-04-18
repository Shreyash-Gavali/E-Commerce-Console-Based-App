package com.app.ecommerce;

import com.app.ecommerce.Product;
import java.util.ArrayList;
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
//		a.add(" 14. Not Purchase Item ");
		a.add(" 14. Continue  ");
		a.add(" 15. Exit App ");
		return a;
	}
	public static void main(String[] args) {
		
		String continueApp = "T";
		int userChoice = 0;
		Scanner sc = new Scanner(System.in);
		UserOperation uo = new UserOperation();
		AdminOperation ao = new AdminOperation();
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
					uo.loginUser();
					break;
				case 3: 
					TreeSet<Product> products= uo.viewProducts();
					for(Product p : products)
					{
						System.out.println("Product Id >> "+p.getProductId());
						System.out.println("Product Name >> "+p.getProductName());
						System.out.println("Product Description >> "+p.getProductDescription());
						System.out.println("Available Quantity  >> "+p.getProductQuantity());
						System.out.println("Price >> "+p.getProductPrice());
					}
					break;
				case 4: 
					System.out.println(" Enter the product id to buy product ");
					int productId = sc.nextInt();
					System.out.println(" Enter the quantity ");
					int quantity = sc.nextInt();
					uo.buyProduct(productId, quantity);
					
					System.out.println(" Do You Want to View The Cart ");
					break;
				case 5: 
					break;
				case 6: 
					break;
				case 7: 
					ao.addProduct();
					break;
				case 8: 
					
					break;
				case 9: 
					break;
				case 10: 
					int productQuantity = ao.getQuantity();
					System.out.println(" Quantity is >> "+productQuantity);
					break;
				case 11: 
					ArrayList<User> user =ao.checkRegisteredUser();
					for(User u : user)
					{
						System.out.println(" User ID >> "+u.getUserId());
						System.out.println(" UserName >> "+u.getUserName());
						System.out.println(" First Name >> "+u.getUserFirstName());
						System.out.println(" Last Name >> "+u.getUserLastName());
						System.out.println(" Email Id >> "+u.getUserEmailId());
						System.out.println(" Mobile >> "+u.getUserMobileNumber());
						System.out.println(" City>> "+u.getUserCity());
						System.out.println(" ------------------------------------ ");
					}
					break;
				case 12: 
					break;
				case 13: 
					break;
				case 14: 
					continueApp = "T";
					System.out.println(" ------------------------------------ ");
					break;
				case 15:
					System.out.println("Exiting App ");
					continueApp = "F";
					break;
				default:
						throw new InvalidInputEnteredException(" Please Enter a Number Specified In the Menu");
				}
			}
		

		
		
	}

}
