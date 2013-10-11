package ru.terra.market.engine;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import ru.terra.market.db.entity.Category;
import ru.terra.market.db.entity.Product;
import ru.terra.market.db.entity.controller.CategoryJpaController;

@Singleton
@Component
public class CategoriesEngine {

	private CategoryJpaController cjc;

	public CategoriesEngine() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("market-dbPU");
		cjc = new CategoryJpaController(emf);
	}

	public List<Category> getCategories() {
		return cjc.findCategoryEntities();
	}

	public Category getCategory(Integer id) {
		if (id != null)
			return cjc.findCategory(id);
		return null;
	}

	public List<Category> getCategoriesByParent(Integer parentId) {
		if (parentId == -1)
			return cjc.findCategoryEntities();
		else
			return cjc.findCategoryByParent(parentId);
	}

	public Category createCategory(String name) {
		Category c = new Category();
		c.setName(name);
		c.setParent(0);
		c.setProductList(new ArrayList<Product>());
		cjc.create(c);
		return c;
	}

}
