package com.reimbursement.utilities;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ReadPropertyFile {
    
	
	public String getURL() {
		
		InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
		InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(isr);
		
		try {
			return reader.readLine().split("=")[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				is.close();
				isr.close();
				reader.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public String getUsername() {
		
		InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
		InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(isr);
		
		try {
			reader.readLine();
			return reader.readLine().split("=")[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				is.close();
				isr.close();
				reader.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public String getPassword() {
		
		InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
		InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(isr);
		
		try {
			reader.readLine();
			reader.readLine();
			return reader.readLine().split("=")[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				is.close();
				isr.close();
				reader.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

