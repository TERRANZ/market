package ru.terra.market.dto.group;

import ru.terra.market.db.entity.Group;
import ru.terra.market.dto.CommonDTO;
import ru.terra.market.engine.ProductsEngine;

public class GroupDTO extends CommonDTO {
	public String name;
	public Integer parent;
	public Long count;

	public GroupDTO(Group c, ProductsEngine pe) {
		this.id = c.getId();
		this.name = c.getName();
		this.parent = c.getParent();
		this.count = pe.getProductCount(c.getId());
	}
}
