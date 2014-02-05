package ru.terra.market.dto.category;

import ru.terra.market.db.entity.Group;
import ru.terra.market.dto.CommonDTO;
import ru.terra.market.engine.ProductsEngine;

public class GroupDTO extends CommonDTO {	
	public String name;
	public Integer parent;
	public Long count;

	private ProductsEngine pe = new ProductsEngine();

	public GroupDTO(Group c) {
		this.id = c.getId();
		this.name = c.getName();
		this.parent = c.getParent();
		this.count = pe.getProductCount(c.getId());
	}
}
