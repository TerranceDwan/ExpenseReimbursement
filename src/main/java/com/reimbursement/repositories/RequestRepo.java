package com.reimbursement.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import com.reimbursement.models.Employee;
import com.reimbursement.models.Request;

public interface RequestRepo {

	int createRequest(Request r, Employee e) throws SQLException;
	Request getRequestByID(int id);
	ArrayList<Request> getAllRequests();
	ArrayList<Request> getRequestsByEmployee(Employee e);
	ArrayList<Request> getRequestsByManager(Employee e);
	ArrayList<Request> getArchivedRequests();
	void approveRequest(Request r);
	void denyRequest(Request r);
	void deleteRequest(Request r);
}
