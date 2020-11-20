package com.reimbursement.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import com.reimbursement.models.Employee;

public interface EmployeeRepo {

	Employee login(String username, String password) throws SQLException;
	int createEmployee(Employee e, Employee m) throws SQLException;
	Employee getEmployeeByID(int id);
	ArrayList<Employee> getAllEmployees();
	ArrayList<Employee> getSubordinates(Employee manager);
	void updateEmail(Employee e, String email);
	void updateAddress(Employee e, String address);
}
