package ru.terra.et.core.cache;

import ru.terra.et.core.network.dto.category.CategoryDTO;

public class CategoriesCache extends AbstractCache<Integer, CategoryDTO>
{
	private static CategoriesCache instance = new CategoriesCache();

	private CategoriesCache()
	{
	}

	public static CategoriesCache getInstance()
	{
		return instance;
	}
}
