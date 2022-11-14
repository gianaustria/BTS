package com.bts.fund;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class DonateBean {
	private int id;
	private String currency;
	private String donateAmount;
	private String donatePay;
	
	public DonateBean()
	{
		
	}
	
	public DonateBean(int id, String currency, String donateAmount, String donatePay) {
		
		this.id = id;
		this.currency = currency;
		this.donateAmount = donateAmount;
		this.donatePay = donatePay;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDonateAmount() {
		return donateAmount;
	}
	public void setDonateAmount(String donateAmount) {
		this.donateAmount = donateAmount;
	}
	public String getDonatePay() {
		return donatePay;
	}
	public void setDonatePay(String donatePay) {
		this.donatePay = donatePay;
	}

	public String Redirect() {
		return "LandingPage";
	}
}
