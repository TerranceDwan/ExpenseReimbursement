package com.reimbursement.service;

import java.sql.SQLException;

import com.reimbursement.models.Employee;
import com.reimbursement.models.Request;
import com.reimbursement.repositories.EmployeeRepo;
import com.reimbursement.repositories.EmployeeRepoImpl;
import com.reimbursement.repositories.RequestRepo;
import com.reimbursement.repositories.RequestRepoImpl;

public class LoginLogoutService {
	private EmployeeRepo employeeRepo;
	private RequestRepo requestRepo;
	
	public LoginLogoutService() {
		
		this.employeeRepo = new EmployeeRepoImpl();
		this.requestRepo = new RequestRepoImpl();
	}
	
	
}
