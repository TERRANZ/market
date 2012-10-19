package ru.terra.market.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import ru.terra.market.db.entity.Category;
import ru.terra.market.db.entity.Product;
import ru.terra.market.engine.CategoriesEngine;
import ru.terra.market.engine.ProductsEngine;

public class MassiveTest
{

	@Test
	public void test()
	{
		CategoriesEngine ce = new CategoriesEngine();
		ProductsEngine pe = new ProductsEngine();
		List<Integer> categories = new ArrayList<Integer>();
		List<Integer> products = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++)
		{
			Category c = ce.createCategory("category " + String.valueOf(i));
			assertNotNull(c.getId());
			categories.add(c.getId());
			List<Product> prods = new ArrayList<Product>();
			for (int j = 0; j < 100; j++)
			{
				Product p = pe.createProduct(c.getId(), "Product " + String.valueOf(i) + "." + String.valueOf(j), 0, true);
				prods.add(p);
			}
			pe.bulkCreate(prods);
		}
	}
}
