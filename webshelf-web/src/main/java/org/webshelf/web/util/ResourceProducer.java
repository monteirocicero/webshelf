package org.webshelf.web.util;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.webshelf.business.model.User;

public class ResourceProducer {
    
    @Inject
    private HttpSession session;
    
    @Produces
    @LoggedInUser
    @SessionScoped
    @Named("loggedInUser")
    protected User getLoggedInUser() {
	User loggedInUser = (User) session.getAttribute("loggedInUser");
	
	if (loggedInUser == null) {
	    loggedInUser = new User();
	}
	
	return loggedInUser;
    }

}
