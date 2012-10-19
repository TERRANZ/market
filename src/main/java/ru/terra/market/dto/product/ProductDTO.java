package ru.terra.market.dto.product;

import ru.terra.market.db.entity.Product;
import ru.terra.market.dto.category.CategoryDTO;

public class ProductDTO
{
	public Integer id;
	public String name;
	public Integer rating;
	public Boolean avail;
	public CategoryDTO category;
	public boolean ok = true;

	public ProductDTO()
	{
	}

	public ProductDTO(Product p)
	{
		this.id = p.getId();
		this.name = p.getName();
		this.rating = p.getRating();
		this.avail = p.getAvail();
		// this.category = new CategoryDTO(p.getCategory());
	}
}
