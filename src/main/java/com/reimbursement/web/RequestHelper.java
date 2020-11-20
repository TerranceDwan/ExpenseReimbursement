package com.reimbursement.web;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reimbursement.models.Employee;
import com.reimbursement.models.Request;
import com.reimbursement.service.EmployeeService;
import com.reimbursement.service.RequestService;

public class RequestHelper {

	public static Object processGet(HttpServletRequest request, HttpServletResponse response) {
		
		//How do we decide what to return?
		
		final String URL = request.getRequestURI();
		
		final String RESOURCE = URL.replace("/ExpenseReimbursement/myapi", "");
		
		switch(RESOURCE) {
		case "/employee":
			return new EmployeeService().getEmployee(request);
		case "/allemployees":
			return new EmployeeService().getAllEmployees();
		case "/personalrequests":
			return new RequestService().getRequestsByEmployee(request);
		case "/pendingrequests":
			return new RequestService().getRequestsByManager(request);
		case "/archivedrequests":
			return new RequestService().getArchiveRequest(request);
		case "/getemployees":
			return new EmployeeService().getAllEmployees();
		default:
			return "No such endpoint or resource";
		}	
	}
	public static void processPost(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		final String URL = request.getRequestURI();
		
		final String RESOURCE = URL.replace("/ExpenseReimbursement/myapi", "");
		
		String pastURL = request.getHeader("referer").replace("http://localhost:8080/ExpenseReimbursement", "..");
		
		switch(RESOURCE) {
		case "/session":	
			System.out.println("session");
			Employee employee = new EmployeeService().login(request);
			if(employee != null) {	
				HttpSession session = request.getSession();
				
				session.setAttribute("employee", employee);	
				try {
					response.sendRedirect("../views/portal.html");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try {
					response.setStatus(401);;
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case "/logout":
			HttpSession session = request.getSession(false);
			
			if(session != null) {
				session.invalidate();
			}			
			try {
				response.sendRedirect("../index.html");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/createRequest":
			new RequestService().createRequest(request);
			System.out.println(request.getHeader("referer"));
			try {
				response.sendRedirect(pastURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/createEmployee":
			try {
				new EmployeeService().createEmployee(request);
			} catch (Exception e1) {
				System.out.println("Exception");
				e1.printStackTrace();
				response.setStatus(401);
			}
			break;
		case "/approve":
			new RequestService().approveRequest(request);
			break;
		case "/deny":
			new RequestService().denyRequest(request);
			break;
		case "/updatepersonalinfo":
			new EmployeeService().updateInfo(request);;
		}
	}
}
