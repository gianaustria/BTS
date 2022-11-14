package com.bts.shop;

import java.math.BigDecimal;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Item {
	
	private int id;
	private String name;
	private BigDecimal price;


	public Item(){
		
	}
	
	public Item(int id, String name, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.price = price;

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


}
