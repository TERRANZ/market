package ru.terra.market.controller;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.market.constants.ModelConstants;
import ru.terra.market.constants.URLConstants;
import ru.terra.market.db.entity.Product;
import ru.terra.market.engine.ProductsEngine;

@Controller
public class ProductController {
	@Inject
	private ProductsEngine pe;

	@RequestMapping(value = URLConstants.Pages.PRODUCT, method = { RequestMethod.GET, RequestMethod.POST })
	private String product(HttpServletRequest request, Locale locale, Model model) {
		if (request.getParameter(URLConstants.DoJson.Products.PRODUCT_PARAM_ID) == null)
			return URLConstants.Views.ERROR404;
		Integer prodId = 0;
		try {
			prodId = Integer.parseInt(request.getParameter(URLConstants.DoJson.Products.PRODUCT_PARAM_ID));
			Product prod = pe.getBean(prodId);
			if (prod == null)
				return URLConstants.Views.ERROR404;
			model.addAttribute(ModelConstants.CATEGORY_ID, prod.getGroup().getId());
			model.addAttribute(URLConstants.DoJson.Products.PRODUCT_PARAM_ID, prodId);
		} catch (NumberFormatException e) {
			return URLConstants.Views.ERROR404;
		}
		return URLConstants.Views.PRODUCT;

	}

}
