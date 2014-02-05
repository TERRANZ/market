package ru.terra.market.controller;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.market.constants.ModelConstants;
import ru.terra.market.constants.URLConstants;
import ru.terra.market.db.entity.Group;
import ru.terra.market.engine.CategoriesEngine;

@Controller
public class CategoryController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	@Inject
	private CategoriesEngine ce;

	@RequestMapping(value = URLConstants.Pages.CATEGORY, method = { RequestMethod.GET, RequestMethod.POST })
	private String category(HttpServletRequest request, Locale locale, Model model) {
		String id = request.getParameter(URLConstants.DoJson.Category.CATEGORY_PARAM_ID);
		if (id != null) {
			// egory id = " + id);

			Integer catId = null;
			try {
				catId = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				return URLConstants.Views.ERROR404;
			}

			Group category = ce.getBean(catId);
			if (category != null) {
				model.addAttribute(ModelConstants.CATEGORY_ID, id);
				model.addAttribute(ModelConstants.CATEGORY_NAME, category.getName());
			} else {
				return URLConstants.Views.ERROR404;
			}
		}
		return URLConstants.Views.CATEGORY;

	}
}
