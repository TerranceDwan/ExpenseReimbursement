package com.reimbursement.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.reimbursement.models.Employee;
import com.reimbursement.models.Request;
import com.reimbursement.utilities.ReadPropertyFile;

public class RequestRepoImpl implements RequestRepo {
	ReadPropertyFile rpf = new ReadPropertyFile();

	public int createRequest(Request r, Employee e) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "insert into requests values(default,'p',?,?,?,?,?,?,?,?);";
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
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, r.getPurchaseDate());
			stmt.setString(2, r.getPurpose());
			stmt.setFloat(3, r.getAmount());
			stmt.setBytes(4, r.getReceiptImg());
			stmt.setDate(5, r.getSubmittedDate());
			stmt.setString(6, r.getComments());
			stmt.setInt(7, e.getEmployeeID());
			stmt.setInt(8, e.getManagerID());
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			return rs.getInt("req_id");
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
	
	public ArrayList<Request> getAllRequests() {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "select * from requests";
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
			ArrayList<Request> requests = new ArrayList();
			while(rs.next()) {
				requests.add(new Request(
						rs.getInt("req_id"), rs.getString("status").charAt(0), 
						rs.getDate("purchase_date"), rs.getString("purpose"),
						rs.getFloat("amount"), rs.getBytes("reciept_img"),
						rs.getDate("submitted_date"), rs.getString("additional_comments"),
						rs.getInt("emp_id"), rs.getInt("manager_id")
						));
			}
			return requests;
		} catch (SQLException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
			return null;
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public Request getRequestByID(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "select * from requests where req_id = ?";
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
			Request r = null;
			while(rs.next()) {
				r = new Request(
						rs.getInt("req_id"), rs.getString("status").charAt(0), 
						rs.getDate("purchase_date"), rs.getString("purpose"),
						rs.getFloat("amount"), rs.getBytes("reciept_img"),
						rs.getDate("submitted_date"), rs.getString("additional_comments"),
						rs.getInt("emp_id"), rs.getInt("manager_id")
						);
			}
			return r;
		} catch (SQLException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
			return null;
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException exception) {
				exception.printStackTrace();
			}
		}
	}

	public ArrayList<Request> getRequestsByEmployee(Employee e) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "select * from requests where emp_id = ?";
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
			stmt.setInt(1, e.getEmployeeID());
			ResultSet rs = stmt.executeQuery();
			ArrayList<Request> requests = new ArrayList();
			while(rs.next()) {
				requests.add(new Request(
						rs.getInt("req_id"), rs.getString("status").charAt(0), 
						rs.getDate("purchase_date"), rs.getString("purpose"),
						rs.getFloat("amount"), rs.getBytes("reciept_img"),
						rs.getDate("submitted_date"), rs.getString("additional_comments"),
						rs.getInt("emp_id"), rs.getInt("manager_id")
						));
			}
			return requests;
		} catch (SQLException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
			return null;
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException exception) {
				exception.printStackTrace();
			}
		}
	}

	public ArrayList<Request> getRequestsByManager(Employee e) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "select * from employees natural join requests where manager_id = ?";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}				
		
		System.out.println(e.getEmployeeID());
		
		try {
			conn = DriverManager.getConnection(
					rpf.getURL(), 
					rpf.getUsername(), 
					rpf.getPassword());
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, e.getEmployeeID());
			ResultSet rs = stmt.executeQuery();
			ArrayList<Request> requests = new ArrayList();
			while(rs.next()) {
				requests.add(new Request(
						rs.getInt("req_id"), rs.getString("status").charAt(0), 
						rs.getDate("purchase_date"), rs.getString("purpose"),
						rs.getFloat("amount"), rs.getBytes("reciept_img"),
						rs.getDate("submitted_date"), rs.getString("additional_comments"),
						rs.getInt("emp_id"), rs.getInt("manager_id")
						));
			}
			return requests;
		} catch (SQLException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
			return null;
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public ArrayList<Request> getArchivedRequests() {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "select * from requests where status != 'p'";
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
			ArrayList<Request> requests = new ArrayList();
			while(rs.next()) {
				requests.add(new Request(
						rs.getInt("req_id"), rs.getString("status").charAt(0), 
						rs.getDate("purchase_date"), rs.getString("purpose"),
						rs.getFloat("amount"), rs.getBytes("reciept_img"),
						rs.getDate("submitted_date"), rs.getString("additional_comments"),
						rs.getInt("emp_id"), rs.getInt("manager_id")
						));
			}
			return requests;
		} catch (SQLException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
			return null;
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException exception) {
				exception.printStackTrace();
			}
		}
	}

	public void approveRequest(Request r) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "update requests \n"
				+ "set status = 'a'\n"
				+ "where req_id = ?;";
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
			stmt.setInt(1, r.getRequestID());
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

	public void denyRequest(Request r) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "update requests \n"
				+ "set status = 'd'\n"
				+ "where req_id = ?;";
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
			stmt.setInt(1, r.getRequestID());
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

	public void deleteRequest(Request r) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		final String query = "delete from requests\n"
				+ "where req_id = ?;";
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
			stmt.setInt(1, r.getRequestID());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		
	}

}
