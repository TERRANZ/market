package ru.terra.market.controller;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import flexjson.JSONSerializer;

import ru.terra.market.ResponceUtils;
import ru.terra.market.constants.ModelConstants;
import ru.terra.market.constants.URLConstants;
import ru.terra.market.db.entity.Category;
import ru.terra.market.dto.category.CategoryDTO;
import ru.terra.market.dto.category.CategoryListDTO;
import ru.terra.market.engine.CategoriesEngine;

@Controller
public class CategoryController
{
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	@Inject
	private CategoriesEngine ce;

	@RequestMapping(value = URLConstants.Pages.CATEGORY, method = RequestMethod.GET)
	public String category(HttpServletRequest request, Locale locale, Model model)
	{
		String id = request.getParameter("id");
		if (id != null)
		{
			// egory id = " + id);

			Integer catId = null;
			try
			{
				catId = Integer.parseInt(id);
			} catch (NumberFormatException e)
			{
				return URLConstants.Views.ERROR404;
			}

			Category category = ce.getCategories(catId);
			if (category != null)
			{
				model.addAttribute(ModelConstants.CATEGORY_ID, id);
				model.addAttribute(ModelConstants.CATEGORY_NAME, category.getName());
			}
			else
			{
				return URLConstants.Views.ERROR404;
			}
		}
		return URLConstants.Views.CATEGORY;
	}

	@RequestMapping(value = URLConstants.DoJson.Category.CATEGORY_GET_CATEGORY_TREE, method = RequestMethod.GET)
	public ResponseEntity<String> getCategoryTree(HttpServletRequest request)
	{
		String parentId = request.getParameter("id");
		CategoryListDTO ret = new CategoryListDTO();
		if (parentId == null)
			parentId = "-1";
		// logger.info("parent id = " + parentId);
		try
		{
			Integer pid = Integer.parseInt(parentId);
			List<Category> cs = ce.getCategoriesByParent(pid);
			if (cs != null)
			{
				for (Category c : cs)
				{
					ret.data.add(new CategoryDTO(c));
				}
				ret.size = ret.data.size();
			}
			else
			{
				ret.size = -1;
			}
		} catch (NumberFormatException e)
		{
			ret.size = -1;
		}

		String json = new JSONSerializer().deepSerialize(ret);
		return ResponceUtils.makeResponce(json);
	}

}
