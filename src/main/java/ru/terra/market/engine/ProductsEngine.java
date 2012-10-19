package ru.terra.market.engine;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import ru.terra.market.db.entity.Category;
import ru.terra.market.db.entity.Product;
import ru.terra.market.db.entity.controller.CategoryJpaController;
import ru.terra.market.db.entity.controller.ProductJpaController;

@Singleton
@Component
public class ProductsEngine
{
	private ProductJpaController pjc;
	private CategoryJpaController cjc;

	public ProductsEngine()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("market-dbPU");
		pjc = new ProductJpaController(emf);
		cjc = new CategoryJpaController(emf);
	}

	public List<Product> getAllProducts()
	{
		return pjc.findProductEntities();
	}

	public List<Product> getProducts(Integer categoryId)
	{
		Category cat = cjc.findCategory(categoryId);
		if (cat != null)
			return cat.getProductList();
		return null;
	}

	public Product getProduct(Integer id)
	{
		return pjc.findProduct(id);
	}

	public Product createProduct(Integer category, String name, Integer rating, Boolean avail)
	{
		Product p = new Product();
		p.setCategory(cjc.findCategory(category));
		p.setAvail(avail);
		p.setName(name);
		p.setRating(rating);
		return p;
	}

	public void updateProduct(Product p)
	{
		pjc.create(p);
	}

	public void bulkCreate(List<Product> prods)
	{
		pjc.create(prods);
	}
}
