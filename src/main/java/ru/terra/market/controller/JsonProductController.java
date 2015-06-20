package ru.terra.market.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.terra.market.constants.URLConstants;
import ru.terra.market.db.entity.Product;
import ru.terra.market.dto.product.ProductDTO;
import ru.terra.market.dto.product.ProductListDTO;
import ru.terra.market.engine.ProductsEngine;
import ru.terra.market.util.ResponceUtils;
import flexjson.JSONSerializer;

@Controller
public class JsonProductController {

	@Inject
	private ProductsEngine pe;

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_GET_PRODUCTS, method = { RequestMethod.GET, RequestMethod.POST })
	private ResponseEntity<String> getProducts(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "3") Integer perpage, @RequestParam(required = false, defaultValue = "false") Boolean all,
			@RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = true, defaultValue = "-1") Integer group) {
		ProductListDTO ret = new ProductListDTO();
		if (!group.equals(-1)) {
			try {
				List<Product> products = pe.getProducts(group, all, page, perpage);
				if (products != null) {
					for (Product p : products) {
						ret.data.add(new ProductDTO(p));
					}
					ret.size = ret.data.size();
					ret.full = pe.getProductCount(group);
				} else {
					ret.size = -1;
				}
			} catch (NumberFormatException e) {
				ret.size = -1;
			}
		} else if (name != null && !name.isEmpty()) {
			List<Product> products = pe.findProductsByName(name);
			if (products != null) {
				for (Product p : products) {
					ret.data.add(new ProductDTO(p));
				}
				ret.size = ret.data.size();
			} else {
				ret.size = -1;
			}
		} else {
			List<Product> products = pe.listBeans(true, -1, -1);
			if (products != null) {
				for (Product p : products) {
					ret.data.add(new ProductDTO(p));
				}
				ret.size = ret.data.size();
			}
		}
		String json = new JSONSerializer().deepSerialize(ret);
		return ResponceUtils.makeResponce(json);
	}

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_GET_PRODUCT, method = { RequestMethod.GET, RequestMethod.POST })
	private ResponseEntity<String> getProduct(HttpServletRequest request, @RequestParam(required = true, defaultValue = "0") Integer id) {
		ProductDTO ret = new ProductDTO();
		Product p = pe.getBean(id);
		if (p != null)
			ret = new ProductDTO(p);
		else
			ret.ok = false;
		return ResponceUtils.makeResponce(new JSONSerializer().deepSerialize(ret));
	}

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_GET_MAIN_PRODUCTS, method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<String> getProductMainProducts(HttpServletRequest request, @RequestParam(required = true, defaultValue = "0") Integer page,
			@RequestParam(required = true, defaultValue = "3") Integer perpage, @RequestParam(required = true, defaultValue = "false") Boolean all) {
		ProductListDTO ret = new ProductListDTO();
		List<Product> products = pe.listBeans(all, page, perpage);
		if (products != null) {
			for (Product p : products)
				ret.data.add(new ProductDTO(p));
			ret.size = ret.data.size();
		} else
			ret.size = -1;
		String json = new JSONSerializer().deepSerialize(ret);
		return ResponceUtils.makeResponce(json);
	}
}
