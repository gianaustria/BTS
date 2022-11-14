package com.bts.shop;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;

import com.bts.shop.CartController;

@SessionScoped
@ManagedBean
public class CartController {
	
		private List<Item> items;
		private ProductDBUtil productDBUtil;
		private Logger logger = Logger.getLogger(getClass().getName());
	
		public CartController() throws Exception {
			items = new ArrayList<>();
			
			productDBUtil = ProductDBUtil.getInstance();
		}

		public List<Item> getItems() {
			return items;
		}
		
		public void loadItems(){
			
			logger.info("Loading items");
			items.clear();
			
			try
			{
				items = productDBUtil.getItems();
	            
			}
			catch(Exception ex)
			{
				
				addErrorMessage(ex);
			}
		}

		public String buy(Product product) {
			logger.info("Adding product: " + product);
			
			try{
				if (product.getStatus().equals("Unavailable")){
					return "shop?faces-redirect=true";
					
				}else {
					
				productDBUtil.addProduct(product);
				
				}
				
			} catch (Exception exc){
				
				logger.log(Level.SEVERE, "Error adding students", exc);
				
				addErrorMessage(exc);
				
				return null;
			}
			
			return "cart?faces-redirect=true";
		}

		public String deleteItem(int itemId) {
			
			logger.info("Deleting item: " + itemId);
			
			try {
				
				
				productDBUtil.deleteItem(itemId);
				
			} catch (Exception exc) {
				
				logger.log(Level.SEVERE, "Error deleting movie: " + itemId, exc);
				
				
				addErrorMessage(exc);
				
				return null;
			}
			
			return "shop?faces-redirect=true";
		}
		
		/*public String checkout(Check check){
			
			logger.info("Checking item: " + check);
			
			try {
				
				
				productDBUtil.checkoutItem(check);
				
			} catch (Exception exc) {
				
				logger.log(Level.SEVERE, "Error checking out item: " + check, exc);
				
				
				addErrorMessage(exc);
				
				return null;
			}
			
			return "success?faces-redirect=true";
		}
		*/
		public String checkout() throws Exception {
					
				productDBUtil.checkoutItem();
				
			
			return "success?faces-redirect=true";
		}
		
		public String index() throws Exception {
			items = productDBUtil.getItems();
			return "shop?faces-redirect=true";
		}

		public void getTotal(Item item) throws Exception {
			logger.info("Total item: " + item);
			
			productDBUtil.Total(item);
				
		}
		
		/*
		private int exists(Product product) {
			for (int i = 0; i < this.items.size(); i++) {
				if (this.items.get(i).getProduct().getId() == product.getId()) {
					return i;
				}
			}
			return -1;
		}
		*/
		private void addErrorMessage(Exception exc) {
			FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}
