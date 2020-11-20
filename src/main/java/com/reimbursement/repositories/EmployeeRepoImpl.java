package com.reimbursement.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.reimbursement.models.Employee;
import com.reimbursement.utilities.ReadPropertyFile;

public class EmployeeRepoImpl implements EmployeeRepo {
	ReadPropertyFile rpf = new ReadPropertyFile();

	public int createEmployee(Employee e, Employee m) throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		
		
		String query = "insert into employees values(default,?,?,?,?,?,?,?,'e');\n";
		String query2 = "select emp_id from employees where username = ?";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(
					rpf.getURL(), 
					rpf.getUsername(), 
					rpf.getPassword());
			stmt = conn.prepareStatement(query);
			stmt2 = conn.prepareStatement(query2);
			stmt.setString(1, e.getFname());
			stmt.setString(2, e.getLname());
			stmt.setString(3, e.getUsername());
			stmt.setString(4, e.getPassword());
			stmt.setString(5, e.getEmail());
			stmt.setString(6, e.getAddress());
			stmt.setInt(7, m.getEmployeeID());
			stmt2.setString(1, e.getUsername());
			stmt.execute();
			ResultSet rs = stmt2.executeQuery();
			rs.next();
			return rs.getInt("emp_id");
		} catch (SQLException exception) {
			// TODO Auto-generated catch block
			throw exception;
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException exception) {
				exception.printStackTrace();
			}
		}
	}

	public Employee login(String username, String password) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		final String query = "select * from employees\n"
				+ "where username = ? and passcode = ?\n"
				+ "union \n"
				+ "select * from employees\n"
				+ "where username = ? and passcode = ?";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(
					rpf.getURL(), 
					rpf.getUsername(), 
					rpf.getPassword());
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, username);
			stmt.setString(4, password);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return new Employee(
					rs.getInt("emp_id"),
					rs.getString("fname"),
					rs.getString("lname"),
					rs.getString("username"),
					rs.getString("passcode"),
					rs.getString("email"),
					rs.getString("address"),
					rs.getInt("manager_id"),
					rs.getString("emp_status").charAt(0)
					);
		} catch (SQLException e) {
			throw e;
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public Employee getEmployeeByID(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "select * from employees where emp_id = ?";	
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		try {
			conn = DriverManager.getConnection(
					rpf.getURL(), 
					rpf.getUsername(), 
					rpf.getPassword());
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			Employee emp = new Employee(
						rs.getInt("emp_id"), rs.getString("fname"), 
						rs.getString("lname"), rs.getString("username"),
						rs.getString("passcode"), rs.getString("email"),
						rs.getString("address"), rs.getInt("manager_id"),
						rs.getString("emp_status").charAt(0)
						);
			return emp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Employee> getAllEmployees() {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "select * from employees";	
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		try {
			conn = DriverManager.getConnection(
					rpf.getURL(), 
					rpf.getUsername(), 
					rpf.getPassword());
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Employee> employees = new ArrayList();
			while(rs.next()) {
				employees.add(new Employee(
						rs.getInt("emp_id"), rs.getString("fname"), 
						rs.getString("lname"), rs.getString("username"),
						rs.getString("passcode"), rs.getString("email"),
						rs.getString("address"), rs.getInt("manager_id"),
						rs.getString("emp_status").charAt(0)
						));
			}
			return employees;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Employee> getSubordinates(Employee manager) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "select * from employees where manager_id = ?";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}				
		
		try {
			conn = DriverManager.getConnection(
					rpf.getURL(), 
					rpf.getUsername(), 
					rpf.getPassword());
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, manager.getEmployeeID());
			ResultSet rs = stmt.executeQuery();
			ArrayList<Employee> subordinates = new ArrayList();
			while(rs.next()) {
				subordinates.add(new Employee(
						rs.getInt("emp_id"), rs.getString("fname"), 
						rs.getString("lname"), rs.getString("username"),
						rs.getString("passcode"), rs.getString("email"),
						rs.getString("address"), rs.getInt("manager_id"),
						rs.getString("emp_status").charAt(0)
						));
			}
			return subordinates;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateEmail(Employee e, String email) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "update employees \n"
				+ "set email = ?\n"
				+ "where emp_id = ?;";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(
					rpf.getURL(), 
					rpf.getUsername(), 
					rpf.getPassword());
			stmt = conn.prepareStatement(query);
			stmt.setString(1, email);
			stmt.setInt(2, e.getEmployeeID());
			stmt.execute();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException exception) {
				exception.printStackTrace();
			}
		}
	}

	public void updateAddress(Employee e, String address) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "update employees \n"
				+ "set address = ?\n"
				+ "where emp_id = ?;";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(
					rpf.getURL(), 
					rpf.getUsername(), 
					rpf.getPassword());
			stmt = conn.prepareStatement(query);
			stmt.setString(1, address);
			stmt.setInt(2, e.getEmployeeID());
			stmt.execute();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException exception) {
				exception.printStackTrace();
			}
		}
		
	}

}
