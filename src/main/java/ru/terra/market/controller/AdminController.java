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
import ru.terra.market.db.entity.Group;
import ru.terra.market.db.entity.Product;
import ru.terra.market.dto.CommonDTO;
import ru.terra.market.engine.GroupEngine;
import ru.terra.market.engine.ProductsEngine;
import ru.terra.market.util.ResponceUtils;
import ru.terra.market.web.security.SessionHelper;
import flexjson.JSONSerializer;

@Controller
public class AdminController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private ProductsEngine productsEngine;
	@Inject
	private GroupEngine groupEngine;

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
		model.addAttribute("groups", groupEngine.listBeans(true, 0, 0));
		model.addAttribute("prods", productsEngine.listBeans(true, 0, 0));
		return URLConstants.Views.ADMIN_PRODUCTS;
	}

	@RequestMapping(value = URLConstants.Pages.ADMIN_GROUPS, method = { RequestMethod.GET, RequestMethod.POST })
	private String adminGroups(HttpServletRequest request, Locale locale, Model model) {
		if (!SessionHelper.isUserCurrentAuthorized())
			return "redirect:/" + URLConstants.Pages.LOGIN;
		model.addAttribute("groups", groupEngine.listBeans(true, 0, 0));
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

	@RequestMapping(value = URLConstants.Pages.ADMIN_PRODUCT, method = { RequestMethod.GET, RequestMethod.POST })
	private String adminProduct(HttpServletRequest request, Locale locale, Model model, @RequestParam(required = true, defaultValue = "0") Integer id) {
		if (!SessionHelper.isUserCurrentAuthorized())
			return "redirect:/" + URLConstants.Pages.LOGIN;
		Product p = null;
		if (id > -1)
			p = productsEngine.getBean(id);
		else {
			p = new Product();
			p.setGroup(groupEngine.getBean(0));
			p.setName("Новый продукт");
			p = productsEngine.createBean(p);
		}
		model.addAttribute("product",p);
		model.addAttribute("groups", groupEngine.listBeans(true, 0, 0));
		return URLConstants.Views.ADMIN_PRODUCT;
	}

	@RequestMapping(value = URLConstants.Pages.ADMIN_GROUP, method = { RequestMethod.GET, RequestMethod.POST })
	private String adminGroup(HttpServletRequest request, Locale locale, Model model, @RequestParam(required = true, defaultValue = "0") Integer id) {
		if (!SessionHelper.isUserCurrentAuthorized())
			return "redirect:/" + URLConstants.Pages.LOGIN;
		model.addAttribute("groups", groupEngine.listBeans(true, 0, 0));
		Group g = null;
		if (id > -1)
			g = groupEngine.getBean(id);
		else {
			g = new Group(0, "Новая группа", 0);
			g = groupEngine.createBean(g);
		}
		model.addAttribute("group", g);
		return URLConstants.Views.ADMIN_GROUP;
	}

	@RequestMapping(value = URLConstants.DoJson.Group.UPDATE, method = RequestMethod.POST)
	private String adminGroupUpdate(HttpServletRequest request, @RequestParam(required = true, defaultValue = "0") Integer id,
			@RequestParam(required = true, defaultValue = "0") String name, @RequestParam(required = true, defaultValue = "0") Integer parent) {
		Group g = groupEngine.getBean(id);
		g.setName(name);
		g.setParent(parent);
		groupEngine.updateBean(g);
		return "redirect:/" + URLConstants.Pages.ADMIN_GROUPS;
	}

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_UPDATE, method = RequestMethod.POST)
	private String adminProdUpdate(HttpServletRequest request, @RequestParam(required = true, defaultValue = "0") Integer id,
			@RequestParam(required = true, defaultValue = "0") String name, @RequestParam(required = true, defaultValue = "0") Integer mincount,
			@RequestParam(required = true, defaultValue = "0") String barcode, @RequestParam(required = true, defaultValue = "0") Integer qtype,
			@RequestParam(required = true, defaultValue = "0") Integer priceIn, @RequestParam(required = true, defaultValue = "0") Integer priceOut,
			@RequestParam(required = true, defaultValue = "0") Integer rating, @RequestParam(required = true, defaultValue = "0") String comment,
			@RequestParam(required = true, defaultValue = "0") Integer parent) {
		Group g = groupEngine.getBean(parent);
		Product p = productsEngine.getBean(id);
		p.setName(name);
		p.setMincount(mincount);
		p.setBarcode(barcode);
		p.setQtype(qtype);
		p.setPriceIn(priceIn);
		p.setPriceOut(priceOut);
		p.setRating(rating);
		p.setComment(comment);
		p.setAvail(false);
		p.setGroup(g);
		productsEngine.updateBean(p);
		return "redirect:/" + URLConstants.Pages.ADMIN_PRODUCTS;
	}

}
