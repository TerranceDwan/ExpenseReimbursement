package com.reimbursement.models;

import java.util.ArrayList;

public class Employee {

	private int employeeID;
	private String fname;
	private String lname;
	private String username;
	private String password;
	private String email;
	private String address;
	private int managerID;
	private char status = 'e';
	private ArrayList<Request> subordinateRequests = new ArrayList();
	private ArrayList<Request> personalRequests = new ArrayList();
	private ArrayList<Employee> subordinates = new ArrayList();
	public static ArrayList<Employee> allEmployees = new ArrayList();
	
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getManagerID() {
		return managerID;
	}
	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
	public ArrayList<Request> getSubordinateRequests() {
		return subordinateRequests;
	}
	public void setSubordinateRequests(ArrayList<Request> subordinateRequests) {
		this.subordinateRequests = subordinateRequests;
	}
	public ArrayList<Request> getPersonalRequests() {
		return personalRequests;
	}
	public void setPersonalRequests(ArrayList<Request> personalRequests) {
		this.personalRequests = personalRequests;
	}
	public ArrayList<Employee> getSubordinateInformation() {
		return subordinates;
	}
	public void setSubordinateInformation(ArrayList<Employee> employeeInformation) {
		this.subordinates = employeeInformation;
	}
	
	public Employee(int employeeID, String fname, String lname, String username, String password, String email,
			String address, int managerID, char status) {
		super();
		this.employeeID = employeeID;
		this.fname = fname;
		this.lname = lname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.managerID = managerID;
		this.status = status;
	}
	public Employee(String fname, String lname, String username, String password, String email, String address) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	
	
	
}
