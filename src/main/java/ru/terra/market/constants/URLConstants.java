package ru.terra.market.constants;

public class URLConstants
{
	public static final String SERVER_URL = "http://terranout.ath.cx/market/";

	public class Pages
	{
		public static final String SPRING_LOGIN = "/market/do.login";
		public static final String HOME = "/";
		public static final String ABOUT = "about";
		public static final String CATEGORY = "category";
		public static final String ERROR404 = "error404";
		public static final String LOGIN = "login";
		public static final String MARKET_HOME = "market";
		public static final String MY_PAGE = "my";
		public static final String PRODUCT = "product";
		public static final String REGISTER = "registration";
		public static final String SEARCH = "search";
		public static final String UPLOAD = "upload";
	}

	public class DoJson
	{
		public static final String LOGIN_DO_LOGIN_JSON = "/login/do.login.json";
		public static final String LOGIN_DO_REGISTER_JSON = "/login/do.register.json";
		public static final String LOGIN_DO_GET_MY_ID = "/login/do.getmyid.json";

		public class Products
		{
			public static final String PRODUCT_GET_PRODUCTS = "/product/get.products.json";
			public static final String PRODUCT_GET_MAIN_PRODUCTS = "/product/get.products.main.json";
			public static final String PRODUCT_GET_PRODUCT = "/product/get.product.json";
		}

		public class Category
		{
			public static final String CATEGORY_GET_CATEGORY_TREE = "/category/get.category.tree.json";
			public static final String CATEGORY_GET_CATEGORY = "/category/get.category.json";
			public static final String CATEGORY_GET_BY_PARENT = "/category/get.categories.byparent.json";
		}
	}

	public class Views
	{
		public static final String ABOUT = "about";
		public static final String CATEGORY = "category";
		public static final String ERROR404 = "error404";
		public static final String LOGIN = "login";
		public static final String MARKET_HOME = "market";
		public static final String MY_PAGE = "my";
		public static final String PRODUCT = "product";
		public static final String REGISTER = "reg";
		public static final String SEARCH = "search";
		public static final String UPLOAD = "upload";
	}
}
