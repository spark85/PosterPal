package com.david.ozersky.posterpal.object;

public class User {

	public enum accountStatus {ACTIVE, INACTIVE, EMAIL_UNVERIFIED}
	
	private String id;
	private String city; 
	private String password;
	private accountStatus status;
	private String username;
	private String firstName;
	private String lastName;
	
	public User() {
		
	}
	
	public User(String id, String city, String password, String status, String username, String firstName, String lastName) {
		this.id = id;
		this.city = city;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public accountStatus getStatus() {
		return status;
	}
	public void setStatus(accountStatus status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
