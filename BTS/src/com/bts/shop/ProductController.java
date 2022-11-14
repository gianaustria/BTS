package com.bts.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ProductController {

	private List<Product> products;
	private ProductDBUtil productDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());

	public ProductController() throws Exception {
		products = new ArrayList<>();
			
		productDBUtil = ProductDBUtil.getInstance();
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	public void loadProducts()
	{
		logger.info("Loading products");
		products.clear();
		
		try
		{
             // get all products from database
			products = productDBUtil.getProducts();
            
		}
		catch(Exception ex)
		{
			// add error message for JSF page
			addErrorMessage(ex);
		}
	}
	
	public String index() throws Exception {
		products = productDBUtil.getProducts();
		return "shop?faces-redirect=true";
	}
	
	public String home() {
		return "LandingPage?faces-redirect=true";
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}
