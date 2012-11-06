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

import ru.terra.market.constants.ModelConstants;
import ru.terra.market.constants.URLConstants;
import ru.terra.market.db.entity.Product;
import ru.terra.market.dto.product.ProductDTO;
import ru.terra.market.dto.product.ProductListDTO;
import ru.terra.market.engine.ProductsEngine;
import ru.terra.market.util.ResponceUtils;
import flexjson.JSONSerializer;

@Controller
public class ProductController
{
	@Inject
	private ProductsEngine pe;

	@RequestMapping(value = URLConstants.Pages.PRODUCT, method = RequestMethod.GET)
	public String productGet(HttpServletRequest request, Locale locale, Model model)
	{
		return product(request, locale, model);
	}

	@RequestMapping(value = URLConstants.Pages.PRODUCT, method = RequestMethod.POST)
	public String productPost(HttpServletRequest request, Locale locale, Model model)
	{
		return product(request, locale, model);
	}

	private String product(HttpServletRequest request, Locale locale, Model model)
	{
		if (request.getParameter(URLConstants.DoJson.Products.PRODUCT_PARAM_ID) == null)
			return URLConstants.Views.ERROR404;
		Integer prodId = 0;
		try
		{
			prodId = Integer.parseInt(request.getParameter(URLConstants.DoJson.Products.PRODUCT_PARAM_ID));
			Product prod = pe.getProduct(prodId);
			if (prod == null)
				return URLConstants.Views.ERROR404;
			model.addAttribute(ModelConstants.CATEGORY_ID, prod.getCategory().getId());
			model.addAttribute(URLConstants.DoJson.Products.PRODUCT_PARAM_ID, prodId);
		} catch (NumberFormatException e)
		{
			return URLConstants.Views.ERROR404;
		}
		return URLConstants.Views.PRODUCT;

	}

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_GET_PRODUCTS, method = RequestMethod.GET)
	public ResponseEntity<String> getProductsGet(HttpServletRequest request)
	{
		return getProducts(request);
	}

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_GET_PRODUCTS, method = RequestMethod.POST)
	public ResponseEntity<String> getProductsPost(HttpServletRequest request)
	{
		return getProducts(request);
	}

	private ResponseEntity<String> getProducts(HttpServletRequest request)
	{
		ProductListDTO ret = new ProductListDTO();
		String cat = request.getParameter(URLConstants.DoJson.Products.PRODUCT_PARAM_CATEGORY);
		String name = request.getParameter(URLConstants.DoJson.Products.PRODUCT_PARAM_NAME);
		if (cat != null)
		{
			String limit = request.getParameter(URLConstants.DoJson.Products.PRODUCT_PARAM_LIMIT);
			Integer lim = -1;
			try
			{
				if (limit != null)
				{
					lim = Integer.parseInt(limit);
				}
			} catch (NumberFormatException e)
			{
				ret.size = -1;
			}
			try
			{
				Integer catId = Integer.parseInt(cat);
				List<Product> products = pe.getProducts(catId, lim);
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
		else if (name != null)
		{
			List<Product> products = pe.findProductsByName(name);
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
		String id = request.getParameter(URLConstants.DoJson.Products.PRODUCT_PARAM_ID);
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

		String json = new JSONSerializer().deepSerialize(ret);
		return ResponceUtils.makeResponce(json);
	}

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_GET_MAIN_PRODUCTS, method = RequestMethod.GET)
	public ResponseEntity<String> getProductMainProductsGet(HttpServletRequest request)
	{
		return getProductMainProducts(request);
	}

	@RequestMapping(value = URLConstants.DoJson.Products.PRODUCT_GET_MAIN_PRODUCTS, method = RequestMethod.POST)
	public ResponseEntity<String> getProductMainProductsPost(HttpServletRequest request)
	{
		return getProductMainProducts(request);
	}

	public ResponseEntity<String> getProductMainProducts(HttpServletRequest request)
	{
		ProductListDTO ret = new ProductListDTO();
		String limit = request.getParameter(URLConstants.DoJson.Products.PRODUCT_PARAM_LIMIT);
		Integer lim = -1;
		try
		{
			if (limit != null)
			{
				lim = Integer.parseInt(limit);
			}
		} catch (NumberFormatException e)
		{
		}
		List<Product> products = pe.getAllProductsLimited(lim);
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
		String json = new JSONSerializer().deepSerialize(ret);
		return ResponceUtils.makeResponce(json);
	}

}
