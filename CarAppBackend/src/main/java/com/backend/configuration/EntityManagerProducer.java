package com.backend.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

	@Produces
    public EntityManager createEntityManager() throws NamingException {
		Map<String, String> properties = fillPersistenceProperties();
        return Persistence
                .createEntityManagerFactory("carPU", properties)
                .createEntityManager();
    }

	private Map<String, String> fillPersistenceProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.put("javax.persistence.jdbc.url", System.getenv("DBURL"));
		properties.put("javax.persistence.jdbc.user", System.getenv("DBUSER"));
		properties.put("javax.persistence.jdbc.password", System.getenv("DBPASSWORD"));
		return properties;
	}

	public void close(EntityManager entityManager) {
        entityManager.close();
    }
	
}
