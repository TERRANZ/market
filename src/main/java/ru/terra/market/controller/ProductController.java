package ru.terra.market.controller;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.market.ResponceUtils;
import ru.terra.market.constants.URLConstants;
import ru.terra.market.db.entity.Product;
import ru.terra.market.dto.product.ProductDTO;
import ru.terra.market.dto.product.ProductListDTO;
import ru.terra.market.engine.ProductsEngine;
import flexjson.JSONSerializer;

@Controller
public class ProductController
{
	@Inject
	private ProductsEngine pe;

	@RequestMapping(value = URLConstants.Pages.PRODUCT, method = RequestMethod.GET)
	public String product(Locale locale, Model model)
	{
		return URLConstants.Views.PRODUCT;
	}

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_GET_PRODUCTS, method = RequestMethod.GET)
	public ResponseEntity<String> getProducts(HttpServletRequest request)
	{
		ProductListDTO ret = new ProductListDTO();
		String cat = request.getParameter("category");
		if (cat != null)
		{
			try
			{
				Integer catId = Integer.parseInt(cat);
				List<Product> products = pe.getProducts(catId);
				if (products != null)
				{
					for (Product p : products)
					{
						ret.data.add(new ProductDTO(p));
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
		}
		else
		{
			List<Product> products = pe.getAllProducts();
			if (products != null)
			{
				for (Product p : products)
				{
					ret.data.add(new ProductDTO(p));
				}
				ret.size = ret.data.size();
			}
		}
		String json = new JSONSerializer().deepSerialize(ret);
		return ResponceUtils.makeResponce(json);
	}

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_GET_PRODUCT, method = RequestMethod.GET)
	public ResponseEntity<String> getProductGet(HttpServletRequest request)
	{
		return getProduct(request);
	}

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_GET_PRODUCT, method = RequestMethod.POST)
	public ResponseEntity<String> getProductPost(HttpServletRequest request)
	{
		return getProduct(request);
	}

	private ResponseEntity<String> getProduct(HttpServletRequest request)
	{
		String id = request.getParameter("id");
		ProductDTO ret = new ProductDTO();
		if (id != null)
		{
			try
			{
				Integer pid = Integer.parseInt(id);
				Product p = pe.getProduct(pid);
				if (p != null)
				{
					ret = new ProductDTO(p);
				}
				else
				{
					ret.ok = false;
				}

			} catch (NumberFormatException e)
			{
				ret.ok = false;
			}
		}
		else
		{
			ret.ok = false;
		}

		String json = new JSONSerializer().serialize(ret);
		return ResponceUtils.makeResponce(json);
	}
}
