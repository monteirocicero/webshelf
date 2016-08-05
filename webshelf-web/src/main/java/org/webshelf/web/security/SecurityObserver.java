package org.webshelf.web.security;

import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.deltaspike.jsf.api.listener.phase.AfterPhase;
import org.apache.deltaspike.jsf.api.listener.phase.JsfPhaseId;
import org.webshelf.business.model.User;
import org.webshelf.web.util.LoggedInUser;

public class SecurityObserver {
    
    private static final String URL_PRIVATE_PAGES = "/private/";
    private static final String LOGIN_PAGE = "/public/login.xhml";
    private static final String HOME_PAGE = "/private/home.xhtml";
    
    protected void checkAfterRestoreView(@Observes @AfterPhase (JsfPhaseId.RESTORE_VIEW) PhaseEvent event, HttpServletRequest request, @LoggedInUser User user) {
	this.redirectLoggedInUserToHome(user, request);
	this.redirectAnonymousToLogin(user, request);
    }

    private void redirectAnonymousToLogin(User user, HttpServletRequest request) {
	if(request.getRequestURI().contains(LOGIN_PAGE)) {
	    if (user.getLogin() != null) {
		handleNavigation(HOME_PAGE);
	    }
	}
    }
    
    private void redirectLoggedInUserToHome(User user, HttpServletRequest request) {
	if(request.getRequestURI().contains(URL_PRIVATE_PAGES)) {
	    if (user.getLogin() == null) {
		handleNavigation(LOGIN_PAGE);
	    }
	}
    }

    private void handleNavigation(String page) {
	FacesContext context = FacesContext.getCurrentInstance();
	context.getApplication().getNavigationHandler().handleNavigation(context, null, page + "?faces-redirect=true");
    }
}
