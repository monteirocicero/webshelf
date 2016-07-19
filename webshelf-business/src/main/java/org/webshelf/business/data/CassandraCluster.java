package org.webshelf.business.data;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
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
    
    @PreDestroy
    public void destroy() {
	this.session.close();
	this.cluster.close();
    }

}
