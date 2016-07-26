package org.webshelf.web.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.hibernate.validator.constraints.NotBlank;
import org.webshelf.business.model.User;
import org.webshelf.web.service.UserBean;
import org.webshelf.web.util.Messages;

@Named
@RequestScoped
public class LoginController implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Inject
    private HttpServletRequest request;
    
    @Inject
    private UserBean userBean;
    
    @Inject
    private JsfMessage<Messages> messages;
    
    @NotBlank(message = "Password: Can not be blank.")
    private String password;
    
    @NotBlank(message = "Login: Can not be blank.")
    private String login;
    
    public String doLogin() {
	User user = userBean.findUserByLogin(login);
	String encryptedPassword = User.encryptPassword(password);
	
	if (user == null || !user.getPassword().equals(encryptedPassword)) {
	    messages.addWarn().invalidCredentials();
	    return null;
	}
	
	if (this.request.getSession(Boolean.FALSE) != null) {
	    this.request.getSession(Boolean.FALSE).invalidate();;
	}
	
	this.request.getSession().setAttribute("loggedInUser", user);
	
	return "/private/home.xhtml?faces-redirect=true";
    }
    
    public String logout() {
	this.request.getSession().removeAttribute("loggedInUser");
	this.request.getSession().invalidate();
	
	return "/public/login.xhtml?faces-redirect=true";
	
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
