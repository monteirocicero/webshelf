package org.webshelf.web.util;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageTemplate;

@MessageBundle
public interface Messages {
    
    @MessageTemplate("Is now so inform login and start your shelf books online")
    String insertUserSuccess();
    
    @MessageTemplate("Login/Password invalid.")
    String invalidCredentials();

}
