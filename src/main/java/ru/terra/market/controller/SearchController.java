package ru.terra.market.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.terra.market.constants.URLConstants;
import ru.terra.market.db.entity.Product;
import ru.terra.market.dto.product.ProductDTO;
import ru.terra.market.engine.ProductsEngine;

@Controller
public class SearchController {

	@Inject
	private ProductsEngine pe;

	@RequestMapping(value = URLConstants.Pages.SEARCH, method = RequestMethod.POST)
	public String search(HttpServletRequest request, Locale locale, Model model, @RequestParam(required = true, defaultValue = "") String name) {
		if (name == null)
			return URLConstants.Views.ERROR404;
		else {
			List<ProductDTO> ret = new ArrayList<ProductDTO>();
			List<Product> products = pe.findProductsByName(name);
			if (products != null)
				for (Product p : products)
					ret.add(new ProductDTO(p));

			model.addAttribute("result", ret);
			model.addAttribute("name", name);
			return URLConstants.Views.SEARCH;
		}
	}
}
