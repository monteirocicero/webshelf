package org.webshelf.web.service;

import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.webshelf.business.data.CassandraCluster;
import org.webshelf.business.model.User;
import org.webshelf.web.exception.BusinessRuleException;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserBean {
    
    @Inject
    private CassandraCluster cassandra;
    
    @Inject
    private Validator validator;
    
    public User findUserByLogin(String login) {
	Mapper<User> mapper = this.cassandra.mapper(User.class)	;
	return mapper.get(login);
    }

    public void deletetUser(User user) {
	Mapper<User> mapper = this.cassandra.mapper(User.class)	;
	mapper.delete(user);
    }
    
    public void insertUser(User user) {
	executeBeanValidation(user);
	
	BoundStatement insertUser = this.cassandra.boundInsertUser();
	insertUser.bind(user.getLogin(), user.getName(), user.getPassword());
	
	ResultSet result = this.cassandra.execute(insertUser);
	
	if (!result.wasApplied()) {
	    throw new BusinessRuleException("Login already existing");
	}
    }

    private void executeBeanValidation(User user) {
	Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
	
	if (!constraintViolations.isEmpty()) {
	    throw new ConstraintViolationException(constraintViolations);
	}
    }

}
