package com.app.ecommerce;

public class User {

	private int userId = 0;
	
	private String userFirstName = null;
	private String userLastName = null;
	private String userName = null;
	private String userPassword = null;
	private String userCity = null;
	private String userEmailId = null;
	private long userMobileNumber = 0;
	private String role = "";
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserCity() {
		return userCity;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public long getUserMobileNumber() {
		return userMobileNumber;
	}
	public void setUserMobileNumber(long userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}
	
	public User()
	{
		
	}
	public User(int userId, String userFirstName, String userLastName, String userName, String userPassword,
			String userCity, String userEmailId, long userMobileNumber,String role) {
		super();
		this.userId = userId;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userCity = userCity;
		this.userEmailId = userEmailId;
		this.userMobileNumber = userMobileNumber;
		this.role=role;
	}
	
}
