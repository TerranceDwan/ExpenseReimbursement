package com.reimbursement.service;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.reimbursement.models.Employee;
import com.reimbursement.models.Request;
import com.reimbursement.repositories.EmployeeRepo;
import com.reimbursement.repositories.EmployeeRepoImpl;
import com.reimbursement.repositories.RequestRepo;
import com.reimbursement.repositories.RequestRepoImpl;

public class RequestService {
	
	private RequestRepo requestRepo;
	private EmployeeRepo employeeRepo;
	
	public RequestService() {
		
		this.requestRepo = new RequestRepoImpl();
		this.employeeRepo = new EmployeeRepoImpl();
	}

	public void createRequest(HttpServletRequest req) throws SQLException {	
		HttpSession session = req.getSession(false);
		Employee employee = (Employee) session.getAttribute("employee");
		
		Date purchaseDate = null;
		String purpose = "";
		float amount = 0;
		byte[] receiptImg = null;
		String additionalComments = "";
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(req);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = iter.next();
		    switch(item.getFieldName()) {
		    case "purchaseDate":
		    	purchaseDate = Date.valueOf(item.getString());
		    	break;
		    case "purpose":
		    	purpose = item.getString();
		    	break;
		    case "amount":
		    	amount = Float.parseFloat(item.getString());
		    	break;
		    case "receiptImg":
		    	receiptImg = item.get();
		    	break;
		    case "additionalComments":
		    	additionalComments = item.getString();
		    	break;
		    }  
		}
		
		Request r = null;
		
		r = new Request(
				purchaseDate,
				purpose,
				amount,
				receiptImg,
				additionalComments,
				employee.getEmployeeID(),
				employee.getManagerID()
				);
		
		if(r.getPurchaseDate().after(r.getSubmittedDate())) {
			
		}else if(r.getPurpose().trim().isBlank()) {
			
		}else if(r.getAmount()<1) {
			
		}else {
			r.setRequestID(requestRepo.createRequest(r,employee));
		}
		employee.getPersonalRequests().add(r);
		session.setAttribute("employee", employee);
	}
	
	public ArrayList<Request> getRequestsByEmployee(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Employee e = (Employee) session.getAttribute("employee");
		
		ArrayList<Request> al = requestRepo.getRequestsByEmployee(e);

		return al;
	}
	
	public ArrayList<Request> getRequestsByManager(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		Employee manager = (Employee) session.getAttribute("employee");
				
		if(manager.getSubordinateInformation().size() > 0) {
			System.out.println("manager");
			return requestRepo.getRequestsByManager(manager);
		}
		return null;
	}
	
	public ArrayList<Request> getArchiveRequest(HttpServletRequest request){ 
		
		return requestRepo.getArchivedRequests();
	}
	
	public void approveRequest(HttpServletRequest request) {
		int id = -1;
		try {
			id = Integer.valueOf(request.getReader().readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Request r = requestRepo.getRequestByID(id);

		requestRepo.approveRequest(r);	
	}
	
	public void denyRequest(HttpServletRequest request) {
		int id = -1;
		try {
			id = Integer.valueOf(request.getReader().readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Request r = requestRepo.getRequestByID(id);

		requestRepo.denyRequest(r);
	}
	
	public void deleteRequest(int requestID, int employeeID) {
		Request request = this.getRequestByID(requestID);
		Employee e = EmployeeService.getEmployeeByID(employeeID);

		for(Request r : e.getPersonalRequests()) {
			if(r.getRequestID() == requestID) {
				requestRepo.deleteRequest(request);
				Request.allRequests.remove(request);
				break;
			}
		}
	}
	
	public static Request getRequestByID(int id) {
		for(Request r : Request.allRequests) {
			if(r.getRequestID() == id) {
				return r;
			}
		}
		return null;
	}
	public static Request getRequestByID(Employee e, int id) {
		for(Request r : e.getSubordinateRequests()) {
			if(r.getRequestID() == id) {
				return r;
			}
		}
		return null;
	}
}
