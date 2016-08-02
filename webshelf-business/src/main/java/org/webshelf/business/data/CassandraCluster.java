package org.webshelf.business.data;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.webshelf.business.model.User;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CassandraCluster {
    
    private Cluster cluster;
    private Session session;
    private MappingManager mappingManager;
    private Map<String, PreparedStatement> preparedStatementCache = new HashMap();
    
    @PostConstruct
    public void init() {
	this.cluster = Cluster.builder().addContactPoint("localhost").build();
	this.session = cluster.connect();
	this.mappingManager = new MappingManager(session);
    }
    
    private BoundStatement prepare(String cql) {
	if (!preparedStatementCache.containsKey(cql)) {
	    this.preparedStatementCache.put(cql, session.prepare(cql));
	}
	return this.preparedStatementCache.get(cql).bind();
    }
    
    @Lock(LockType.READ)
    public ResultSet execute(Statement stmt) {
	return session.execute(stmt);
    }
    
    public BoundStatement boundInsertUser() {
	return prepare("INSERT INTO webshelf.user(login, name, password) VALUES (?, ?, ?) IF NOT EXISTS;");
    }

    public Mapper<User> mapper(Class<User> clazz) {
	return mappingManager.mapper(clazz);
    }
    
    @PreDestroy
    public void destroy() {
	this.session.close();
	this.cluster.close();
    }

}
