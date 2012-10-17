package ru.terra.market.dto.category;

import java.util.List;

import javax.inject.Inject;

import ru.terra.market.db.entity.Category;
import ru.terra.market.engine.ProductsEngine;

public class CategoryDTO
{
	public Integer id;
	public String name;
	public Integer parent;
	public Integer count;

	private ProductsEngine pe = new ProductsEngine();

	public CategoryDTO(Category c)
	{
		this.id = c.getId();
		this.name = c.getName();
		this.parent = c.getParent();
		List res = pe.getProducts(id);
		this.count = res != null ? res.size() : 0;
	}
}
