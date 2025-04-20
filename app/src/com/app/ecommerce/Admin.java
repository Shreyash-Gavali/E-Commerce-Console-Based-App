package com.app.ecommerce;

public class Admin extends User{

	public Admin() {
		super();
	}
	public Admin(int userId, String userFirstName, String userLastName, String userName, String userPassword,
			String userCity, String userEmailId, long userMobileNumber, String role) {
		super(userId, userFirstName, userLastName, userName, userPassword, userCity, userEmailId, userMobileNumber,
				role);
	}
	
}
