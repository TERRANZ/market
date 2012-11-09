package ru.terra.market.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ru.terra.market.controller.CategoryController;
import ru.terra.market.db.entity.Category;
import ru.terra.market.db.entity.Product;
import ru.terra.market.db.entity.controller.CategoryJpaController;
import ru.terra.market.db.entity.controller.ProductJpaController;
import ru.terra.market.db.entity.controller.exceptions.PreexistingEntityException;

@Singleton
@Component
public class ProductsEngine
{

	private static final Logger logger = LoggerFactory.getLogger(ProductsEngine.class);

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

	public Long getProductCount(Integer categoryId)
	{
		Category cat = cjc.findCategory(categoryId);
		if (cat != null)
			return pjc.getProductCount(cat);
		return -1L;
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
		try
		{
			pjc.create(p);
		} catch (PreexistingEntityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void bulkCreate(List<Product> prods)
	{
		pjc.create(prods);
	}

	public List<Product> getProducts(Integer catId, Integer lim)
	{
		List<Product> ret = new ArrayList<Product>();
		ret.addAll(loadProductsFromCategory(catId, lim));
		if (ret.size() > 0)
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
		}
		return ret;
	}

	private List<Product> loadProductsFromCategory(Integer catId, Integer lim)
	{
		//logger.info("loadProductsFromCategory : category " + catId);
		Category cat = cjc.findCategory(catId);
		List<Product> ret = new ArrayList<Product>();
		if (cat != null)
		{
			List<Product> prods = pjc.findProductByCategory(cat, lim);
			if (prods != null)
			{
				ret.addAll(prods);
				//logger.info("loaded " + prods.size() + " products in category");
			}
			for (Category c : cjc.findCategoryByParent(cat.getId()))
			{
				//logger.info("loading products from " + c.getId() + " category ");
				ret.addAll(loadProductsFromCategory(c.getId(), lim));
			}
		}
		return ret;
	}

	public List<Product> getAllProductsLimited(Integer lim)
	{
		List<Product> ret = new ArrayList<Product>();
		ret = pjc.findProductEntities(lim, 0);
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

	public List<Product> findProductsByName(String name)
	{
		List<Product> ret = new ArrayList<Product>();
		ret = pjc.findProductsByName(name);
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