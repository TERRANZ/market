package ru.terra.market.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.market.constants.URLConstants;
import ru.terra.market.db.entity.Group;
import ru.terra.market.dto.category.CategoryDTO;
import ru.terra.market.dto.category.CategoryListDTO;
import ru.terra.market.dto.category.CategoryTreeDTO;
import ru.terra.market.engine.CategoriesEngine;
import ru.terra.market.util.ResponceUtils;
import flexjson.JSONSerializer;

@Controller
public class JsonCategoryController {
	@Inject
	private CategoriesEngine ce;

	@RequestMapping(value = URLConstants.DoJson.Category.CATEGORY_GET_CATEGORY_TREE, method = { RequestMethod.GET, RequestMethod.POST })
	private ResponseEntity<String> getCategoryTree(HttpServletRequest request) {
		String json = new JSONSerializer().deepSerialize(getCategoriesRecursive(1));
		return ResponceUtils.makeResponce(json);
	}

	@RequestMapping(value = URLConstants.DoJson.Category.CATEGORY_GET_BY_PARENT, method = { RequestMethod.GET, RequestMethod.POST })
	private ResponseEntity<String> getCategoryByParent(HttpServletRequest request) {
		String parentId = request.getParameter(URLConstants.DoJson.Category.CATEGORY_PARAM_ID);
		CategoryListDTO ret = new CategoryListDTO();
		if (parentId == null)
			parentId = "-1";
		try {
			Integer pid = Integer.parseInt(parentId);
			for (Group cat : ce.getCategoriesByParent(pid)) {
				ret.data.add(new CategoryDTO(cat));
			}
			ret.size = ret.data.size();
			String json = new JSONSerializer().deepSerialize(ret);
			return ResponceUtils.makeResponce(json);
		} catch (NumberFormatException e) {
			return ResponceUtils.makeErrorResponce("");
		}
	}

	private CategoryTreeDTO getCategoriesRecursive(Integer parent) {
		List<CategoryTreeDTO> childs = new ArrayList<CategoryTreeDTO>();
		for (Group child : ce.getCategoriesByParent(parent)) {
			childs.add(getCategoriesRecursive(child.getId()));
		}
		return new CategoryTreeDTO(childs, new CategoryDTO(ce.getBean(parent)));
	}

	@RequestMapping(value = URLConstants.DoJson.Category.CATEGORY_GET_CATEGORIES, method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<String> getCategories(HttpServletRequest request) {
		CategoryListDTO ret = new CategoryListDTO();
		for (Group cat : ce.listBeans(true, -1, -1))
			ret.data.add(new CategoryDTO(cat));
		ret.size = ret.data.size();
		String json = new JSONSerializer().deepSerialize(ret);
		return ResponceUtils.makeResponce(json);
	}
}
