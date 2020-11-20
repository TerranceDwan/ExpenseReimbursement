package com.reimbursement.service.test;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import com.reimbursement.models.Employee;
import com.reimbursement.models.Request;
import com.reimbursement.repositories.EmployeeRepoImpl;
import com.reimbursement.service.EmployeeService;

public class EmployeeServiceTest {
	@InjectMocks
	private static EmployeeService employeeService;
	
	@Mock private EmployeeRepoImpl employeeRepo;
	
	@BeforeClass
	public static void setUp() {
		employeeService = new EmployeeService();
	}
	
	@Before
	public void before() {	
		Date date = new Date(0);
		Date today = new Date(1);
		byte[] pic = {1,1,2,1,0};
		MockitoAnnotations.openMocks(this);
		Request.allRequests.clear();
		Employee.allEmployees.clear();
		Request a = new Request(1, 'p', date, "Certification", 200, pic, today, "comment", 1, 3);
		Request b = new Request(2, 'a', date, "Paper", 200, pic, today, "comment", 2, 3);
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
	}
	
	@Test
	public void testCreateEmployee() throws SQLException {
		Mockito.doReturn(0).when(employeeRepo).createEmployee((Employee) Mockito.any(), (Employee) Mockito.any());
		
		employeeService.createEmployee();
		
		Mockito.verify(employeeRepo).createEmployee((Employee) Mockito.any(), (Employee) Mockito.any());
	}
	
	@Test
	public void testCreateEmployeeCheckID() throws SQLException {
		Mockito.doReturn(123).when(employeeRepo).createEmployee((Employee) Mockito.any(), (Employee) Mockito.any());
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("firstName", "Mario");
		request.addParameter("lastName", "Luigi");
		request.addParameter("username", "mluigi");
		request.addParameter("password", "secret");
		request.addParameter("email", "mario@jblow.com");
		request.addParameter("address", "123 Imagine Street");
		
		employeeService.createEmployee(e, 3);
		
		Assert.assertEquals(123, e.getEmployeeID());
		Assert.assertTrue(Employee.allEmployees.contains(e));
	}
	
	@Test
	public void testCreateEmployeeUsername() throws SQLException {
		
		Employee e = Employee.allEmployees.get(0);
		e.setUsername("test");
		employeeService.createEmployee(e, 3);
		
		Mockito.verifyNoInteractions(employeeRepo);
	}
	
	@Test
	public void testCreateEmployeePassword() throws SQLException {
		
		Employee e = Employee.allEmployees.get(0);
		e.setPassword("test");
		employeeService.createEmployee(e, 3);
		
		Mockito.verifyNoInteractions(employeeRepo);
	}
	
	@Test
	public void testCreateEmployeeEmail() throws SQLException {
		
		Employee e = Employee.allEmployees.get(0);
		e.setEmail("test");
		employeeService.createEmployee(e, 3);
		
		Mockito.verifyNoInteractions(employeeRepo);
	}
	
	@Test
	public void testUpdateEmail() {
		Mockito.doNothing().when(employeeRepo).updateEmail((Employee) Mockito.any(), Mockito.anyString());
		
		Employee e = Employee.allEmployees.get(0);
		employeeService.updateEmail(e, "test@test.com");
		
		Assert.assertEquals("test@test.com", e.getEmail());
	}
	
	@Test
	public void testUpdateAddress() {
		Mockito.doNothing().when(employeeRepo).updateEmail((Employee) Mockito.any(), Mockito.anyString());
		
		Employee e = Employee.allEmployees.get(0);
		employeeService.updateAddress(e, "test address");
		
		Assert.assertEquals("test address", e.getAddress());
	}
}
