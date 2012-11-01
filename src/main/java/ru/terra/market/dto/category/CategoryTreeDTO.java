package ru.terra.market.dto.category;

import java.util.List;

public class CategoryTreeDTO
{
	public List<CategoryTreeDTO> childs;
	public CategoryDTO category;
	private boolean hasChilds = false;

	public CategoryTreeDTO(List<CategoryTreeDTO> childs, CategoryDTO category)
	{
		this.childs = childs;
		this.category = category;
	}

	public boolean isHasChilds()
	{
		hasChilds = childs != null && childs.size() > 0;
		return hasChilds;
	}
}
