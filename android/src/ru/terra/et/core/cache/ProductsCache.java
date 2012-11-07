package ru.terra.et.core.cache;

import ru.terra.et.core.network.dto.product.ProductDTO;

public class ProductsCache extends AbstractCache<Integer, ProductDTO>
{
	private static ProductsCache instance = new ProductsCache();

	private ProductsCache()
	{
	}

	public static ProductsCache getInstance()
	{
		return instance;
	}
}
