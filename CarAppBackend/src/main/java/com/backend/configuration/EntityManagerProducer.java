package com.backend.configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

	@Produces
    public EntityManager createEntityManager() {
        return Persistence
                .createEntityManagerFactory("carPU")
                .createEntityManager();
    }

    public void close(EntityManager entityManager) {
        entityManager.close();
    }
	
}
