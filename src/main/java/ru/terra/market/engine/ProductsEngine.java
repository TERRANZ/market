package ru.terra.market.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.terra.market.core.AbstractEngine;
import ru.terra.market.db.controller.ProductJpaController;
import ru.terra.market.db.controller.exceptions.PreexistingEntityException;
import ru.terra.market.db.entity.Group;
import ru.terra.market.db.entity.Product;
import ru.terra.market.dto.product.ProductDTO;

@Component
public class ProductsEngine extends AbstractEngine<Product, ProductDTO> {

	@Inject
	private GroupEngine groupsEngine;

	public ProductsEngine() {
		super(new ProductJpaController());
	}

	public List<Product> getProducts(Integer groupId) {
		Group group = groupsEngine.getBean(groupId);
		if (group != null)
			return group.getProductList();
		return null;
	}

	public Long getProductCount(Integer groupId) {
		if (groupsEngine == null)
			LoggerFactory.getLogger(this.getClass()).error("Groups engine is null");
		Group group = groupsEngine.getBean(groupId);
		if (group != null)
			return ((ProductJpaController) jpaController).getProductCountByGroup(group);
		return -1L;
	}

	public Product createProduct(Integer category, String name, String comment, Integer rating, Boolean avail) throws PreexistingEntityException,
			Exception {
		Product p = new Product();
		p.setGroup(groupsEngine.getBean(category));
		p.setAvail(avail);
		p.setName(name);
		p.setComment(comment);
		p.setRating(rating);
		p.setPriceOut(0);
		p.setPriceIn(0);
		createBean(p);
		return p;
	}

	public void bulkCreate(List<Product> prods) {
		((ProductJpaController) jpaController).create(prods);
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
		Group cat = groupsEngine.getBean(catId);
		List<Product> ret = new ArrayList<Product>();
		if (cat != null) {
			List<Product> prods = ((ProductJpaController) jpaController).findProductByGroup(cat, all, page, perpage);
			if (prods != null)
				ret.addAll(prods);
			for (Group c : groupsEngine.getGroupsByParent(cat.getId()))
				ret.addAll(loadProductsFromCategory(c.getId(), all, page, perpage));
		}
		return ret;
	}

	public List<Product> findProductsByName(String name) {
		List<Product> ret = new ArrayList<Product>();
		ret = ((ProductJpaController) jpaController).findProductsByName(name);
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

	@Override
	public void dtoToEntity(ProductDTO dto, Product entity) {
		if (dto == null)
			return;
		if (entity == null)
			entity = new Product();
		entity.setAvail(dto.avail);
		entity.setComment(dto.comment);
		entity.setGroup(groupsEngine.getBean(dto.category));
		entity.setId(dto.id);
		entity.setName(dto.name);
		entity.setPriceOut(dto.price);
		entity.setRating(dto.rating);
	}

	@Override
	public ProductDTO entityToDto(Product entity) {
		return new ProductDTO(entity);
	}
}