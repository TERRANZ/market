package ru.terra.et.provider;

import ru.terra.et.core.cache.CategoriesCache;
import ru.terra.et.core.constants.URLConstants;
import ru.terra.et.core.network.JsonAbstractProvider;
import ru.terra.et.core.network.dto.category.CategoryDTO;
import ru.terra.et.core.network.dto.category.CategoryListDTO;
import ru.terra.et.core.network.dto.category.CategoryTreeDTO;
import android.app.Activity;

import com.google.gson.Gson;

public class CategoriesProvider extends JsonAbstractProvider
{

	public CategoriesProvider(Activity c)
	{
		super(c);
	}

	public CategoryTreeDTO loadTree()
	{
		String json = "";
		json = httpReqHelper.runSimpleJsonRequest(URLConstants.DoJson.Category.CATEGORY_GET_CATEGORY_TREE);
		return new Gson().fromJson(json, CategoryTreeDTO.class);
	}

	public void load()
	{
		String json = "";
		json = httpReqHelper.runSimpleJsonRequest(URLConstants.DoJson.Category.CATEGORY_GET_CATEGORIES);
		CategoryListDTO ret = new Gson().fromJson(json, CategoryListDTO.class);
		if (ret != null && ret.data != null && ret.data.length > 0)
		{
			for (CategoryDTO c : ret.data)
			{
				CategoriesCache.getInstance().addToCache(c.id, c);
			}
		}
	}

}
