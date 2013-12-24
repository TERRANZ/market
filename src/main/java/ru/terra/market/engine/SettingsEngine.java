package ru.terra.market.engine;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import ru.terra.market.db.controller.SettingsJpaController;

@Singleton
@Component
public class SettingsEngine {
	private SettingsJpaController sjpc;

	public SettingsEngine() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("market-dbPU");
		sjpc = new SettingsJpaController(emf);
	}

}
