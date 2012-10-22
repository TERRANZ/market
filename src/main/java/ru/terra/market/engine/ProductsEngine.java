package ru.terra.market.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

	public List<Product> getProducts(Integer catId, Integer lim)
	{
		Category cat = cjc.findCategory(catId);
		if (cat != null)
		{
			List<Product> ret = pjc.findProductByCategory(cat, lim);
			if (ret != null)
			{
				Collections.sort(ret, new Comparator<Product>()
				{
					@Override
					public int compare(Product o1, Product o2)
					{
						if (o1.getRating() < o2.getRating())
							return -1;
						else if (o1.getRating() == o2.getRating())
							return 0;
						else
							return 1;
					}
				});				
				return ret;
			}
		}
		return null;
	}
}