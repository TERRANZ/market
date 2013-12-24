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

import ru.terra.market.db.controller.CategoryJpaController;
import ru.terra.market.db.controller.ProductJpaController;
import ru.terra.market.db.controller.exceptions.PreexistingEntityException;
import ru.terra.market.db.entity.Group;
import ru.terra.market.db.entity.Product;

@Component
public class ProductsEngine {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ProductJpaController pjc;
	private CategoryJpaController cjc;

	public ProductsEngine() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("market-dbPU");
		pjc = new ProductJpaController(emf);
		cjc = new CategoryJpaController(emf);
	}

	public List<Product> getAllProducts() {
		return pjc.findProductEntities();
	}

	public List<Product> getProducts(Integer categoryId) {
		Group cat = cjc.findCategory(categoryId);
		if (cat != null)
			return cat.getProductList();
		return null;
	}

	public Long getProductCount(Integer categoryId) {
		Group cat = cjc.findCategory(categoryId);
		if (cat != null)
			return pjc.getProductCount(cat);
		return -1L;
	}

	public Product getProduct(Integer id) {
		return pjc.findProduct(id);
	}

	public Product createProduct(Integer category, String name, String comment, Integer rating, Boolean avail) throws PreexistingEntityException, Exception {
		Product p = new Product();
		p.setGroup(cjc.findCategory(category));
		p.setAvail(avail);
		p.setName(name);
		p.setComment(comment);
		p.setRating(rating);
		p.setPrice(0);
		pjc.create(p);
		return p;
	}

	public void updateProduct(Product p) {
		try {
			pjc.edit(p);
		} catch (PreexistingEntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bulkCreate(List<Product> prods) {
		pjc.create(prods);
	}

	public List<Product> getProducts(Integer catId, Boolean all, Integer page, Integer perpage) {
		List<Product> ret = new ArrayList<Product>();
		ret.addAll(loadProductsFromCategory(catId, all, page, perpage));
		if (ret.size() > 0) {
			Collections.sort(ret, new Comparator<Product>() {
				@Override
				public int compare(Product o1, Product o2) {
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

	private List<Product> loadProductsFromCategory(Integer catId, Boolean all, Integer page, Integer perpage) {
		Group cat = cjc.findCategory(catId);
		List<Product> ret = new ArrayList<Product>();
		if (cat != null) {
			List<Product> prods = pjc.findProductByCategory(cat, all, page, perpage);
			if (prods != null)
				ret.addAll(prods);
			for (Group c : cjc.findCategoryByParent(cat.getId()))
				ret.addAll(loadProductsFromCategory(c.getId(), all, page, perpage));
		}
		return ret;
	}

	public List<Product> getAllProductsLimited(Boolean all, Integer page, Integer perpage) {
		List<Product> ret = new ArrayList<Product>();
		ret = pjc.findProductEntities(all, perpage, perpage * page);
		Collections.sort(ret, new Comparator<Product>() {
			@Override
			public int compare(Product o1, Product o2) {
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

	public List<Product> findProductsByName(String name) {
		List<Product> ret = new ArrayList<Product>();
		ret = pjc.findProductsByName(name);
		Collections.sort(ret, new Comparator<Product>() {
			@Override
			public int compare(Product o1, Product o2) {
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