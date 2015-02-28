package ru.terra.market.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import ru.terra.market.constants.URLConstants;
import ru.terra.market.dto.CommonDTO;
import ru.terra.market.engine.ProductsEngine;
import ru.terra.market.util.ResponceUtils;
import ru.terra.market.web.security.SessionHelper;
import flexjson.JSONSerializer;

@Controller
public class AdminController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private ProductsEngine productsEngine;

	@RequestMapping(value = URLConstants.Pages.ADMIN, method = { RequestMethod.GET, RequestMethod.POST })
	private String admin(HttpServletRequest request, Locale locale, Model model) {
		if (!SessionHelper.isUserCurrentAuthorized())
			return "redirect:/" + URLConstants.Pages.LOGIN;
		return URLConstants.Views.ADMIN;
	}

	@RequestMapping(value = URLConstants.Pages.ADMIN_PRODUCTS, method = { RequestMethod.GET, RequestMethod.POST })
	private String adminProds(HttpServletRequest request, Locale locale, Model model) {
		if (!SessionHelper.isUserCurrentAuthorized())
			return "redirect:/" + URLConstants.Pages.LOGIN;
		return URLConstants.Views.ADMIN_PRODUCTS;
	}

	@RequestMapping(value = URLConstants.Pages.ADMIN_GROUPS, method = { RequestMethod.GET, RequestMethod.POST })
	private String adminGroups(HttpServletRequest request, Locale locale, Model model) {
		if (!SessionHelper.isUserCurrentAuthorized())
			return "redirect:/" + URLConstants.Pages.LOGIN;
		return URLConstants.Views.ADMIN_GROUPS;
	}

	@RequestMapping(value = URLConstants.Pages.NEW_PRODUCTS, method = { RequestMethod.GET, RequestMethod.POST })
	private String newProds(HttpServletRequest request, Locale locale, Model model) {
		if (!SessionHelper.isUserCurrentAuthorized())
			return "redirect:/" + URLConstants.Pages.LOGIN;
		return URLConstants.Views.NEW_PRODUCTS;
	}

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_CREATE, method = { RequestMethod.POST })
	private ResponseEntity<String> createProduct(HttpServletRequest request, @RequestParam(required = true, defaultValue = "0") String title,
			@RequestParam(required = true, defaultValue = "0") String comment, @RequestParam(required = true, defaultValue = "0") Integer category) {
		logger.info("Create product category:" + category + " title:" + title + " comment:" + comment);
		if (!SessionHelper.isUserCurrentAuthorized())
			return ResponceUtils.makeErrorResponce(new JSONSerializer().serialize(new CommonDTO()));
		try {
			productsEngine.createProduct(category, title, comment, 0, true);
		} catch (Exception e) {
			return ResponceUtils.makeErrorResponce(new JSONSerializer().serialize(new CommonDTO()));
		}
		return ResponceUtils.makeResponce(new JSONSerializer().serialize(new CommonDTO()));
	}
}
