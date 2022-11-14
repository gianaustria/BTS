package com.bts.fund;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DonateDBUtil {
	
	private static DonateDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/projdb";
	
	public static DonateDBUtil getInstance() throws Exception
	{
		if(instance == null)
		{
			instance = new DonateDBUtil();
		}
		
		return instance;
	}
	
	private DonateDBUtil() throws Exception
	{
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException
	{
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public List<DonateBean> getDonations() throws Exception
	{
		List<DonateBean> donations = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try
		{
			myConn = getConnection();
			String sql = "select * from funds";
			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next())
			{
				int id = myRs.getInt("id");
				String currency = myRs.getString("currency");
				String donateAmount = myRs.getString("donateAmount");
				String donatePay = myRs.getString("donatePay");
				
				DonateBean tempDonate = new DonateBean(id, currency, donateAmount, donatePay);
				
				donations.add(tempDonate);
			}
			
			return donations;
		}
		
		finally
		{
			close(myConn, myStmt, myRs);
		}
		
	}
	
	public void addDonation(DonateBean theDonate) throws Exception 
	{

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try 
		{
			myConn = getConnection();

			String sql = "insert into funds (currency, donateAmount, donatePay) values (?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			//set parameters
			myStmt.setString(1, theDonate.getCurrency());
			myStmt.setString(2, theDonate.getDonateAmount());
			myStmt.setString(3, theDonate.getDonatePay());

			myStmt.execute();
		}

		finally 
		{
			//close (myConn, myStmt);
		}
	}
	
	public DonateBean getDonations(int donateId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = getConnection();

			String sql = "select * from funds where id=?";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, donateId);

			myRs = myStmt.executeQuery();

			DonateBean theDonate = null;

			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("id");
				String currency = myRs.getString("currency");
				String donateAmount = myRs.getString("donateAmount");
				String donatePay = myRs.getString("donatePay");

				theDonate = new DonateBean(id, currency, donateAmount,
						donatePay);
			}
			else {
				throw new Exception("Could not find donate id: " + donateId);
			}

			return theDonate;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}
	
	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();

		return theConn;
	}

	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
