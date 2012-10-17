package ru.terra.market.engine;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import ru.terra.market.db.entity.Category;
import ru.terra.market.db.entity.controller.CategoryJpaController;
import ru.terra.market.dto.category.CategoryDTO;

@Singleton
@Component
public class CategoriesEngine
{

	private CategoryJpaController cjc;

	public CategoriesEngine()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("market-dbPU");
		cjc = new CategoryJpaController(emf);
	}

	public List<Category> getCategories()
	{
		return cjc.findCategoryEntities();
	}

	public Category getCategories(Integer id)
	{
		return cjc.findCategory(id);
	}

	public List<Category> getCategoriesByParent(Integer parentId)
	{
		return cjc.findCategoryByParent(parentId);
	}

}
