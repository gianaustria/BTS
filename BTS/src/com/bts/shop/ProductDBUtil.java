package com.bts.shop;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ProductDBUtil {
	
	private static ProductDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/projdb";
	
	public static ProductDBUtil getInstance() throws Exception 
	{
		if(instance == null)
		{
			instance = new ProductDBUtil();
		}
		
		return instance;
	}
	
	private ProductDBUtil() throws Exception
	{
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException
	{
		Context context = new InitialContext();
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		return theDataSource;
	}
	
	public List<Item> getItems() throws Exception
	{
		
		List<Item> items = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try
		{
			myConn = getConnection();
			String sql = "select * from orderdetails";
			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			
			while (myRs.next())
			{
		
				int id = myRs.getInt("id");
				String name = myRs.getString("name");
				BigDecimal price = myRs.getBigDecimal("price");
				
				
				Item tempItem = new Item(id, name, price);
				
				
				items.add(tempItem);
				
			}
			return items;
			
		}
		
		finally
		{
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<Product> getProducts() throws Exception
	{
		List<Product> products = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try
		{
			myConn = getConnection();
			String sql = "select * from product";
			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			
			while (myRs.next())
			{
				
				int id = myRs.getInt("id");
				String name = myRs.getString("name");
				BigDecimal price = myRs.getBigDecimal("price");
				int quantity = myRs.getInt("quantity");
				String description = myRs.getString("description");
				String status = myRs.getString("status");
				
				Product tempProduct = new Product(id, name, price, quantity, description, status);
				
				products.add(tempProduct);
				
			}
			
			return products;
			
		}
		
		finally
		{
			close(myConn, myStmt, myRs);
		}
	}
	
	public void addProduct(Product product) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			String sql ="insert into orderdetails (id, name, price) values(?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, product.getId());
			myStmt.setString(2, product.getName());
			myStmt.setBigDecimal(3, product.getPrice());

			myStmt.execute();
			
		}finally {
		
		}
	}
	
	public void deleteItem(int itemId) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = getConnection();

			String sql = "delete orderdetails from orderdetails LEFT JOIN product on orderdetails.id = product.id where product.id=?";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, itemId);
			
			myStmt.execute();
			
		}
		finally {
			//close (myConn, myStmt);
		}
	}
	
	/*public void  checkoutItem(Check check) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			String sql1 ="insert into checkout (id, name, price, quantity) values(?, ?, ?, ?)";
			
			myStmt.setInt(1, check.getId());
			myStmt.setString(2, check.getName());
			myStmt.setBigDecimal(3, check.getPrice());
			myStmt.setInt(4, check.getQuantity());
			
			myStmt = myConn.prepareStatement(sql1);
			
			String sql ="update product set quantity - ?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(1, check.getQuantity());

			myStmt.execute();
			
		}finally {
		
		}
	}*/
	
	public void  checkoutItem() throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			String sql ="delete * from orderdetails";
			
			myStmt = myConn.prepareStatement(sql);

			myStmt.execute();
			
		}finally {
		
		}
	}

	public double Total(Item item) throws Exception {
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		double value = 0.0;
		try
		{
			myConn = getConnection();
			String sql = "select sum(price) from checkout";
			
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			myRs.next();
			String sum = myRs.getString(1);
			value = Double.parseDouble(sum);
			
			return value;
			
		}
		
		finally
		{
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
	
}
