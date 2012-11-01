package ru.terra.market.engine;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import ru.terra.market.db.entity.Photo;
import ru.terra.market.db.entity.controller.PhotoJpaController;

@Singleton
@Component
public class PhotoEngine
{
	private PhotoJpaController pjc;
	@Inject
	private ProductsEngine pe;

	public PhotoEngine()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("market-dbPU");
		pjc = new PhotoJpaController(emf);
	}

	public Photo newPhoto(String name, String path, Integer productId)
	{
		Photo p = new Photo();
		p.setName(name != null ? name : "Без имени");
		p.setPath(path);
		p.setProductId(pe.getProduct(productId));
		pjc.create(p);
		return p;
	}
}
