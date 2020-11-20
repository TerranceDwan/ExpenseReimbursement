package com.reimbursement.models;

import java.sql.Date;
import java.util.ArrayList;


public class Request {

	private int requestID;
	private char status;
	private Date purchaseDate;
	private String purpose;
	private float amount;
	private byte[] receiptImg;
	private Date submittedDate = new Date(System.currentTimeMillis());
	private String comments;
	private int employeeID;
	private int managerID;
	public static ArrayList<Request> allRequests = new ArrayList();
	
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public byte[] getReceiptImg() {
		return receiptImg;
	}
	public void setReceiptImg(byte[] receiptImg) {
		this.receiptImg = receiptImg;
	}
	public Date getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public int getManagerID() {
		return managerID;
	}
	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
	
	public Request(int requestID, char status, Date purchaseDate, String purpose, float amount, byte[] receiptImg,
			Date submittedDate, String comments, int employeeID, int managerID) {
		super();
		this.requestID = requestID;
		this.status = status;
		this.purchaseDate = purchaseDate;
		this.purpose = purpose;
		this.amount = amount;
		this.receiptImg = receiptImg;
		this.submittedDate = submittedDate;
		this.comments = comments;
		this.employeeID = employeeID;
		this.managerID = managerID;
	}
	public Request(Date purchaseDate, String purpose, float amount, byte[] receiptImg, String comments,
			int employeeID, int managerID) {
		super();
		this.purchaseDate = purchaseDate;
		this.purpose = purpose;
		this.amount = amount;
		this.receiptImg = receiptImg;
		this.comments = comments;
		this.employeeID = employeeID;
		this.managerID = managerID;
	}
	
	
}
