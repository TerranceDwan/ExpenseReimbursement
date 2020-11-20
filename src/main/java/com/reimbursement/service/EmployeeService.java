package com.reimbursement.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reimbursement.models.Employee;
import com.reimbursement.repositories.EmployeeRepo;
import com.reimbursement.repositories.EmployeeRepoImpl;
import com.reimbursement.repositories.RequestRepo;
import com.reimbursement.repositories.RequestRepoImpl;

public class EmployeeService {
	private EmployeeRepo employeeRepo;
	private RequestRepo requestRepo;
	
	public EmployeeService() {
		
		this.employeeRepo = new EmployeeRepoImpl();
		this.requestRepo = new RequestRepoImpl();
	}
	
	public void createEmployee(HttpServletRequest request) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		Employee manager = (Employee) session.getAttribute("employee");
				
		Employee e = new Employee(
				req.getParameter("firstName"), req.getParameter("lastName"),
				req.getParameter("username"), req.getParameter("password"),
				req.getParameter("email"), req.getParameter("address")
				);
		
		if(e.getUsername().length() < 5) {
			throw new Exception("Username too short");
		}else if(e.getPassword().length() < 5) {
			throw new Exception("Password too short");
		}else if(e.getEmail().contains("@") && e.getEmail().contains(".")) {
			try {
				e.setEmployeeID(employeeRepo.createEmployee(e, manager));
			}catch(Exception exception) {
				throw exception;
			}
		}else {
			throw new Exception("Some issue with employee creation");
		}
		manager.getSubordinateInformation().add(e);
		session.setAttribute("employee", manager);
	}
	
	public Employee getEmployee(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Employee e = (Employee) session.getAttribute("employee");
		return e;
	}
	
	public Employee login(HttpServletRequest request){
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		
		try {
			Employee employee = employeeRepo.login(username, password);
			employee.setSubordinateInformation(employeeRepo.getSubordinates(employee));
			employee.setPersonalRequests(requestRepo.getRequestsByEmployee(employee));
			if(employee.getSubordinateInformation().size() > 0) {
				employee.setSubordinateRequests(requestRepo.getRequestsByManager(employee));
			}
			return employee;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Employee> getAllEmployees() {
		return employeeRepo.getAllEmployees();
	}
	
	public ArrayList<Employee> getSubordiante(int managerID) {
		ArrayList<Employee> al = new ArrayList();
		
		for(Employee e : Employee.allEmployees) {
			if(e.getManagerID() == managerID) {
				al.add(e);
			}
		}
		return al;
	}
	
	public void updateInfo(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Employee employee = (Employee) session.getAttribute("employee");
		
		String email = "";
		String address = "";
		
		try {
			String[] properties = request.getReader().readLine().split(", ");
			email = properties[0].split("=")[1];
			address = properties[1].split("=")[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		employeeRepo.updateEmail(employee, email);
		employeeRepo.updateAddress(employee, address);
	}
	
	public static Employee getEmployeeByID(int id) {
		for(Employee e : Employee.allEmployees) {
			if(e.getEmployeeID() == id) {
				return e;
			}
		}
		return null;
	}
}
