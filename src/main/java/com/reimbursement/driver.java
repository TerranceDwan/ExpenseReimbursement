package com.reimbursement;


import java.io.IOException;
import java.sql.SQLException;

import com.reimbursement.utilities.ReadPropertyFile;

public class driver {

	public static void main(String...args) throws SQLException, IOException {
	ReadPropertyFile rpf = new ReadPropertyFile();
	
	System.out.println(rpf.getURL());
	System.out.println(rpf.getUsername());
	System.out.println(rpf.getPassword());

	
	}
}
