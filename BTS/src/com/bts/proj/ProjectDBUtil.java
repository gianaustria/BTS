package com.bts.proj;

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

public class ProjectDBUtil {

	private static ProjectDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/projdb";
	
	public static ProjectDBUtil getInstance() throws Exception
	{
		if(instance == null)
		{
			instance = new ProjectDBUtil();
		}
		
		return instance;
	}
	
	private ProjectDBUtil() throws Exception
	{
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException
	{
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public List<ProjectBean> getProj() throws Exception
	{
		List<ProjectBean> project = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try
		{
			myConn = getConnection();
			String sql = "select * from user order by lName";
			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			//process results
			while (myRs.next())
			{
				//get data from result set row
				int id = myRs.getInt("id");
				String fName = myRs.getString("fName");
				String lName = myRs.getString("lName");
				String email = myRs.getString("email");
				String password = myRs.getString("password");
				String mobileNum = myRs.getString("mobileNum");
				String bday = myRs.getString("bday");
				String gender = myRs.getString("gender");
				
				//create student object
				ProjectBean tempProj = new ProjectBean(id, fName, lName, email, password, mobileNum, bday, gender);
				
				//add it to the list of students
				project.add(tempProj);
				
			}
			
			return project;
			
		}
		
		finally
		{
			//clean up JDBC objects
			close(myConn, myStmt, myRs);
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
	
	public void addUser(ProjectBean theUser) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try { 
			myConn = getConnection();
			
			String sql = "insert into users (fName, lName, email, password, mobileNum, bday, gender) values (?, ?, ?, ?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, theUser.getfName());
			myStmt.setString(2, theUser.getlName());
			myStmt.setString(3, theUser.getEmail());
			myStmt.setString(4, theUser.getPassword());
			myStmt.setString(5, theUser.getMobileNum());
			myStmt.setString(6, theUser.getBday());
			myStmt.setString(7, theUser.getGender());
			
			myStmt.execute();
		}
		
		finally {
			//close (myConn, myStmt);
		}
	}
	
	public ProjectBean getUser(int userId) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = getConnection();

			String sql = "select * from users where id=?";

			myStmt = myConn.prepareStatement(sql);
			
			// set
			myStmt.setInt(1, userId);
			
			myRs = myStmt.executeQuery();

			ProjectBean theUser = null;
			
			// retrieve data from result set row
			if (myRs.next()) {
				int id = myRs.getInt("id");
				String fName = myRs.getString("fName");
				String lName = myRs.getString("lName");
				String email = myRs.getString("email");
				String password = myRs.getString("password");
				String mobileNum = myRs.getString("mobileNum");
				String bday = myRs.getString("bday");
				String gender = myRs.getString("gender");

				theUser = new ProjectBean(id, fName, lName, email, password, mobileNum, bday, gender);
			}
			else {
				throw new Exception("Could not find user id: " + userId);
			}

			return theUser;
		}
		finally {
			close (myConn, myStmt, myRs);
		}
	}

	public void updateUser(ProjectBean theUser) throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			//update mo table name and table rows name
			String sql = "update users "
						+ " set fName=?, lname=?, email=?, password=?, mobileNum=?, bday=?, gender=?"
						+ " where id=?";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setString(1,theUser.getfName());
			myStmt.setString(2, theUser.getlName());
			myStmt.setString(3, theUser.getEmail());
			myStmt.setString(4, theUser.getPassword());
			myStmt.setString(5, theUser.getMobileNum());
			myStmt.setString(6, theUser.getBday());
			myStmt.setString(7, theUser.getGender());
			myStmt.setInt(8, theUser.getId());
			
			myStmt.execute();
		}
		finally {
			//close (myConn, myStmt);
		}
		
	}
}
