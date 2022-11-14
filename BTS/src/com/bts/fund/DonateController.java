package com.bts.fund;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class DonateController {

	private List<DonateBean> donations;
	private DonateDBUtil donateDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public DonateController() throws Exception {
		donations = new ArrayList<>();
		
		donateDBUtil = DonateDBUtil.getInstance();
	}
	
	public List<DonateBean> getDonations() {
		return donations;
	}
	
	public void loadDonations() {
		
		logger.info("Loading donations");
		donations.clear();
		
		try
		{
			donations = donateDBUtil.getDonations();
		}
		catch(Exception ex)
		{
			addErrorMessage(ex);
		}
	}
	
	public String addDonation(DonateBean theDonate)
	{
		logger.info("Processing donation" + theDonate);
		
		try
		{
			donateDBUtil.addDonation(theDonate);
		}
		
		catch (Exception exc)
		{
			logger.log(Level.SEVERE, "Error processing donation", exc);
			addErrorMessage(exc);
			
			return null;
		}
		
		return "fundraising-confirmation?faces-redirect=true";
	}
	
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}






























