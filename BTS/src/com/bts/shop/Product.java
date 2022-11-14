package com.bts.shop;

import java.math.BigDecimal;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Product {
	
	private int id;
	private String name;
	private BigDecimal price;
	private int quantity;
	private String description;
	private String status;
	
	public Product(){
		
	}
	
	public Product(int id, String name, BigDecimal price, int quantity, String description, String status){
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
