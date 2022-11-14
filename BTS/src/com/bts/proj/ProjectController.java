package com.bts.proj;

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
public class ProjectController {

	private List<ProjectBean> project;
	private ProjectDBUtil projectDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public ProjectController() throws Exception {
		project = new ArrayList<>();
			
		projectDBUtil = ProjectDBUtil.getInstance();
	}
	
	public List<ProjectBean> getProj() {
		return project;
	}
	
	public void loadProject()
	{
		logger.info("Loading project");
		project.clear();
		
		try
		{
             // get all students from database
			project = projectDBUtil.getProj();
            
		}
		catch(Exception ex)
		{
			// add error message for JSF page
			addErrorMessage(ex);
		}
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public String addUser (ProjectBean theUser) {
		logger.info("Adding user: " + theUser);
		
		try {
			projectDBUtil.addUser(theUser);
			
		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error adding user", exc); 
			addErrorMessage(exc);
			
			return null;
		}
		
		return "login?faces-redirect=true";
	}
	
	public String loadUser(int userId)
	{
		logger.info("loading user: " + userId);
		
		try{
			ProjectBean theUser = projectDBUtil.getUser(userId);
			ExternalContext externalContext = 
					FacesContext.getCurrentInstance().getExternalContext();		
		
		
		Map<String, Object> requestMap = externalContext.getRequestMap();
		requestMap.put("user", theUser);	
		
	} catch (Exception exc) {
		logger.log(Level.SEVERE, "Error loading user id:" + userId, exc);
		
		
		addErrorMessage(exc);
		
		return null;
	}
			
	return "update-user-account.xhtml";
		
	}
	
	public String updateUser(ProjectBean theUser) {
		
		logger.info("updating user: " + theUser);
		
		try {
			
			projectDBUtil.updateUser(theUser);
			
		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating user: " + theUser, exc);
		
			addErrorMessage(exc);
			
			return null;
		}
		
		return "user-profile.xhtml?faces-redirect=true";		
	}
}
