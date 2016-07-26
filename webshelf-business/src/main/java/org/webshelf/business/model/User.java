package org.webshelf.business.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hibernate.validator.constraints.NotBlank;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "webshelf", name = "user")
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @PartitionKey
    @NotBlank(message = "Login: Can not be blank")
    private String login;

    @NotBlank(message = "Name: Can not be blank")
    private String name;

    @NotBlank(message = "Password: Can not be blank")
    private String password;

    public void setClearPassword(String password) {
	this.password = password;
    }

    public static String encryptPassword(String clearPassword) {
	try {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(clearPassword.getBytes());
	    return new BigInteger(1, md.digest()).toString(16);
	} catch (NoSuchAlgorithmException e) {
	    throw new RuntimeException("An error occurred while trying to encrypt the password");
	}
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

}
