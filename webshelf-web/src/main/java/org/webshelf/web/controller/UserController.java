package org.webshelf.web.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.jsf.api.message.JsfMessage;
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.logging.Messages;
import org.webshelf.business.model.User;
import org.webshelf.web.service.UserBean;

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
    
    private User user;
    
    @NotBlank(message = "Password: Can not be blank")
    private String password;
    
    public String insert() {
	this.user.setClearPassword(password);
	return null;
    }

}
