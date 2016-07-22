package org.webshelf.web.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.hibernate.validator.constraints.NotBlank;
import org.webshelf.business.model.User;
import org.webshelf.web.service.UserBean;
import org.webshelf.web.util.Messages;

@Named
@ViewScoped
public class UserController implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private JsfMessage<Messages> messages;
    
    private User user = new User();
    
    @NotBlank(message = "Password: Can not be blank")
    private String password;
    
    public String insert() {
	this.user.setClearPassword(password);
	this.userBean.insertUser(this.user);
	messages.addInfo().insertUserSuccess();
	
	return "login.xhtml?faces-redirect=true";
    }
    
    public User getUser() {
	return user;
    }
    
    public String getPassword() {
	return password;
    }
    
    public void setPassword(String password) {
	this.password = password;
    }

}
