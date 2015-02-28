package ru.terra.market.engine;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.springframework.stereotype.Component;

import ru.terra.market.core.AbstractEngine;
import ru.terra.market.db.controller.PhotoJpaController;
import ru.terra.market.db.entity.Photo;
import ru.terra.market.dto.photo.PhotoDTO;

@Singleton
@Component
public class PhotoEngine extends AbstractEngine<Photo, PhotoDTO> {

	@Inject
	private ProductsEngine productsEngine;
	@Inject
	private UsersEngine usersEngine;

	public PhotoEngine() {
		super(new PhotoJpaController());
	}

	public Photo newPhoto(String name, String path, Integer productId) {
		Photo p = new Photo();
		p.setName(name != null ? name : "Без имени");
		p.setPath(path);
		p.setProduct(productsEngine.getBean(productId));
		createBean(p);
		return p;
	}

	@Override
	public void dtoToEntity(PhotoDTO dto, Photo entity) {
		if (dto == null)
			return;
		if (entity == null)
			entity = new Photo();
		entity.setId(dto.id);
		entity.setName(dto.name);
		entity.setPath(dto.path);
		entity.setProduct(productsEngine.getBean(dto.productId));
	}

	@Override
	public PhotoDTO entityToDto(Photo entity) {
		return new PhotoDTO(entity);
	}
}
