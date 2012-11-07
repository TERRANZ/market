package ru.terra.et.provider;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import ru.terra.et.core.constants.URLConstants;
import ru.terra.et.core.network.JsonAbstractProvider;
import ru.terra.et.core.network.dto.product.ProductDTO;
import ru.terra.et.core.network.dto.product.ProductListDTO;
import android.app.Activity;

import com.google.gson.Gson;

public class ProductsProvider extends JsonAbstractProvider
{

	public ProductsProvider(Activity c)
	{
		super(c);
	}

	public ProductDTO[] getProductsByCategory(Integer cid)
	{
		String json = "";
		json = httpReqHelper.runJsonRequest(URLConstants.DoJson.Products.PRODUCT_GET_MAIN_PRODUCTS, new BasicNameValuePair(
				URLConstants.DoJson.Products.PRODUCT_PARAM_CATEGORY, cid.toString()));
		ProductListDTO ret = new Gson().fromJson(json, ProductListDTO.class);
		if (ret != null && ret.data != null && ret.data.length > 0)
		{
			return ret.data;
		}
		return null;

	}

	public ProductDTO getProduct(Integer id)
	{
		String json = "";
		json = httpReqHelper.runJsonRequest(URLConstants.DoJson.Products.PRODUCT_GET_MAIN_PRODUCTS, new BasicNameValuePair(
				URLConstants.DoJson.Products.PRODUCT_PARAM_ID, id.toString()));
		return new Gson().fromJson(json, ProductDTO.class);
	}

	public ProductDTO[] getProductsByKind(String kind, Integer limit)
	{
		String json = "";
		// if (kind.equals("recent"))
		// {
		json = httpReqHelper.runJsonRequest(URLConstants.DoJson.Products.PRODUCT_GET_MAIN_PRODUCTS, new BasicNameValuePair(
				URLConstants.DoJson.Products.PRODUCT_PARAM_LIMIT, limit.toString()));
		ProductListDTO ret = new Gson().fromJson(json, ProductListDTO.class);
		if (ret != null && ret.data != null && ret.data.length > 0)
		{
			return ret.data;
		}
		return null;
		// }
		// else
		// {
		// json = httpReqHelper.runJsonRequest(URLConstants.DoJson.Products.PRODUCT_GET_MAIN_PRODUCTS, new BasicNameValuePair(
		// URLConstants.DoJson.Products.PRODUCT_PARAM_LIMIT, limit.toString()));
		// ProductListDTO ret = new Gson().fromJson(json, ProductListDTO.class);
		// if (ret != null && ret.data != null && ret.data.length > 0)
		// {
		// List<ProductDTO> products = new LinkedList<ProductDTO>();
		// for (ProductDTO p : ret.data)
		// {
		// products.add(p);
		// }
		// return products;
		// }
		// }
	}
}
