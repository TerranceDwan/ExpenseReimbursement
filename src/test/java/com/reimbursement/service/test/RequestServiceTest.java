package com.reimbursement.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.reimbursement.models.Employee;
import com.reimbursement.models.Request;
import com.reimbursement.repositories.RequestRepoImpl;
import com.reimbursement.service.EmployeeService;
import com.reimbursement.service.RequestService;

import org.junit.Assert;

public class RequestServiceTest {
	@InjectMocks
	private static RequestService requestService;
	
	@Mock private RequestRepoImpl requestRepo;
	
	@BeforeClass
	public static void setUp() {
		requestService = new RequestService();
	}
	
	@Before
	public void before() {	
		Date date = new Date(0);
		Date today = new Date(1);
		ArrayList<Request> subsRequests3 = new ArrayList();
		ArrayList<Employee> subs3 = new ArrayList();
		byte[] pic = {1,1,2,1,0};
		MockitoAnnotations.openMocks(this);
		Request.allRequests.clear();
		Employee.allEmployees.clear();
		subsRequests3.clear();
		Request a = new Request(1, 'p', date, "Certification", 200, pic, today, "comment", 1, 3);
		Request b = new Request(2, 'a', date, "Paper", 200, pic, today, "comment", 2,3);
		Request c = new Request(3, 'd', date, "PS5", 200, pic, today, "comment", 2,3);
		Employee emp1 = new Employee(1, "Joe", "Blow", "jblow", "secret", "jblow@jblow.com", "123 Imagine Lane", 3, 'e');
		Employee emp2 = new Employee(2, "Joe", "Shmo", "jshmo", "secret", "jshmo@jshmo.com", "123 Imagine Drive", 3, 'e');
		Employee man1 = new Employee(3, "Big", "Boss", "bboss", "secret", "bboss@jshmo.com", "123 Imagine Circle", 4, 'm');
		Employee man2 = new Employee(4, "Other", "Boss", "oboss", "secret", "oboss@jshmo.com", "123 Imagine Court", 3, 'm');
		Request.allRequests.add(a);
		Request.allRequests.add(b);
		Request.allRequests.add(c);
		Employee.allEmployees.add(emp1);
		Employee.allEmployees.add(emp2);
		Employee.allEmployees.add(man1);
		Employee.allEmployees.add(man2);
		subsRequests3.add(a);
		subsRequests3.add(b);
		subsRequests3.add(c);
		subs3.add(emp1);
		subs3.add(emp2);
		subs3.add(man2);
		ArrayList<Request> personalAccounts = new ArrayList<Request>();
		personalAccounts.add(a);
		emp1.setPersonalRequests(personalAccounts);
		man1.setSubordinateInformation(subs3);
		man1.setSubordinateRequests(subsRequests3);
	}
	
	@Test
	public void testCreateRequest() throws SQLException {
		Mockito.doReturn(1).when(requestRepo).createRequest((Request) Mockito.any(), (Employee) Mockito.any());
		
		requestService.createRequest(Request.allRequests.get(0), 1);
		
		Mockito.verify(requestRepo).createRequest(Mockito.any(Request.class), Mockito.any(Employee.class));
	}
	
	@Test
	public void testCreateRequestCheckID() throws SQLException {
		Mockito.doReturn(123).when(requestRepo).createRequest((Request) Mockito.any(), (Employee) Mockito.any());
		
		Request r = Request.allRequests.get(0);
		requestService.createRequest(r, 1);
		Request.allRequests.remove(0);
		
		Assert.assertEquals(123, r.getRequestID());
		Assert.assertTrue(Request.allRequests.contains(r));
	}
	
	@Test
	public void testCreateRequestPurchaseDate() throws SQLException {
		
		Request r = Request.allRequests.get(0);
		r.setPurchaseDate(Date.valueOf("2050-10-10"));
		r.setSubmittedDate(new Date(System.currentTimeMillis()));
		requestService.createRequest(r, 1);

		Mockito.verifyNoInteractions(requestRepo);
	}
	
	@Test
	public void testCreateRequestPurpose() throws SQLException {
		
		Request r = Request.allRequests.get(0);
		r.setPurpose("     ");
		requestService.createRequest(r, 1);

		Mockito.verifyNoInteractions(requestRepo);
	}
	
	@Test
	public void testCreateRequestAmount() throws SQLException {
		
		Request r = Request.allRequests.get(0);
		r.setAmount(0);
		requestService.createRequest(r, 1);

		Mockito.verifyNoInteractions(requestRepo);
	}
	
	@Test
	public void testGetRequestsByEmployee() {
		
		ArrayList<Request> test1 = requestService.getRequestsByEmployee(1);
		ArrayList<Request> test2 = requestService.getRequestsByEmployee(2);
		
		assertEquals(1, test1.size());
		assertEquals(2, test2.size());
	}
	
	@Test
	public void testGetRequestsByManager() {
		ArrayList<Request> al = new ArrayList();
		Employee manager = EmployeeService.getEmployeeByID(3);
		al = Employee.allEmployees.get(Employee.allEmployees.indexOf(manager)).getSubordinateRequests();
		Mockito.doReturn(al).when(requestRepo).getRequestsByManager((Employee) Mockito.any());
		
		requestService.getRequestsByManager(3);
		
		Mockito.verify(requestRepo).getRequestsByManager((Employee) Mockito.any());
	}
	
	@Test
	public void testGetRequestsByManagerDifferentID() {
		
		requestService.getRequestsByManager(4);
		
		Mockito.verifyNoInteractions(requestRepo);;
	}
	
	@Test
	public void testApproveRequest() {
		Mockito.doNothing().when(requestRepo).approveRequest((Request) Mockito.any());
		
		int size = Request.allRequests.size();
		
		requestService.approveRequest(Request.allRequests.get(0).getRequestID(), 3);
		
		assertEquals(size, Request.allRequests.size());
		assertEquals("Certification", Request.allRequests.get(0).getPurpose());
		assertEquals('a', Request.allRequests.get(0).getStatus());
	}
	
	@Test
	public void testApproveRequestWrongRequest() {
				
		requestService.approveRequest(Request.allRequests.get(0).getRequestID(), 4);
		
		Mockito.verifyNoInteractions(requestRepo);
	}
	
	@Test
	public void testDenyRequest() {
		Mockito.doNothing().when(requestRepo).denyRequest((Request) Mockito.any());
		
		int size = Request.allRequests.size();
		
		requestService.denyRequest(Request.allRequests.get(0).getRequestID(), 3);
		
		assertEquals(size, Request.allRequests.size());
		assertEquals("Certification", Request.allRequests.get(0).getPurpose());
		assertEquals('d', Request.allRequests.get(0).getStatus());
	}
	
	@Test
	public void testDenyRequestWrongRequest() {
				
		requestService.denyRequest(Request.allRequests.get(0).getRequestID(), 4);
		
		Mockito.verifyNoInteractions(requestRepo);
	}
	
	@Test
	public void testDeleteRequest() {
		Mockito.doNothing().when(requestRepo).deleteRequest((Request) Mockito.any());
		
		requestService.deleteRequest(1, 1);
		
		Mockito.verify(requestRepo).deleteRequest((Request) Mockito.any());
	}
	
	@Test
	public void testDeleteRequestUpdatedState() {
		Mockito.doNothing().when(requestRepo).deleteRequest((Request) Mockito.any());

		Request r = requestService.getRequestByID(1);
		
		requestService.deleteRequest(1, 1);
		
		Assert.assertFalse(Request.allRequests.contains(r));
	}
	
	@Test
	public void testDeleteRequestManager() {
		
		requestService.deleteRequest(1, 3);
		requestService.deleteRequest(2, 1);

		Mockito.verifyNoInteractions(requestRepo);
	}
	
}
