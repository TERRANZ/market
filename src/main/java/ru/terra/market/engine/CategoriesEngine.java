package ru.terra.market.engine;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import ru.terra.market.core.AbstractEngine;
import ru.terra.market.db.controller.CategoryJpaController;
import ru.terra.market.db.entity.Group;
import ru.terra.market.db.entity.Product;
import ru.terra.market.dto.category.CategoryDTO;

@Singleton
@Component
public class CategoriesEngine extends AbstractEngine<Group, CategoryDTO> {

	public CategoriesEngine() {
		super(new CategoryJpaController());
	}

	@Override
	public void dtoToEntity(CategoryDTO dto, Group entity) {
		if (dto == null)
			return;
		if (entity == null)
			entity = new Group();
		entity.setId(dto.id);
		entity.setName(dto.name);
		entity.setParent(dto.parent);
	}

	@Override
	public CategoryDTO entityToDto(Group entity) {
		return new CategoryDTO(entity);
	}

	public List<Group> getCategoriesByParent(Integer parentId) {
		if (parentId == -1)
			return listBeans(true, -1, -1);
		else
			return ((CategoryJpaController) jpaController).findCategoryByParent(parentId);
	}

	public Group createCategory(String name) {
		Group c = new Group();
		c.setName(name);
		c.setParent(0);
		c.setProductList(new ArrayList<Product>());
		createBean(c);
		return c;
	}

}
