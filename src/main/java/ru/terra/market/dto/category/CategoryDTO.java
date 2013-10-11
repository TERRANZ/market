package ru.terra.market.dto.category;

import ru.terra.market.db.entity.Category;
import ru.terra.market.engine.ProductsEngine;

public class CategoryDTO {
	public Integer id;
	public String name;
	public Integer parent;
	public Long count;

	private ProductsEngine pe = new ProductsEngine();

	public CategoryDTO(Category c) {
		this.id = c.getId();
		this.name = c.getName();
		this.parent = c.getParent();
		this.count = pe.getProductCount(c.getId());
	}
}
