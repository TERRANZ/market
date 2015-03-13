package ru.terra.market.constants;

public class URLConstants {
	public static final String SERVER_URL = "http://xn--80aafhfrpg0adapheyc1nya.xn--p1ai/market/";

	public class Pages {
		public static final String SPRING_LOGIN = "/market/do.login";
		public static final String HOME = "/";
		public static final String ABOUT = "about";
		public static final String GROUP = "group";
		public static final String ERROR404 = "error404";
		public static final String LOGIN = "login";
		public static final String MARKET_HOME = "market";
		public static final String MY_PAGE = "my";
		public static final String PRODUCT = "product";
		public static final String REGISTER = "registration";
		public static final String SEARCH = "search";
		public static final String UPLOAD = "upload";
		public static final String ADMIN = "admin_main";
		public static final String ADMIN_PRODUCTS = "admin_products";
		public static final String ADMIN_GROUPS = "admin_groups";
		public static final String NEW_PRODUCTS = "newprods";
		public static final String CREATE_PRODUCTS = "createproduct";
		public static final String ADMIN_PRODUCT = "admin_product";
		public static final String ADMIN_GROUP = "admin_group";
	}

	public class DoJson {
		public class Login {
			public static final String LOGIN_DO_LOGIN_JSON = "/login/do.login.json";
			public static final String LOGIN_DO_REGISTER_JSON = "/login/do.register.json";
			public static final String LOGIN_DO_GET_MY_ID = "/login/do.getmyid.json";

			public static final String LOGIN_PARAM_USER = "user";
			public static final String LOGIN_PARAM_PASS = "pass";
		}

		public class Products {
			public static final String PRODUCT_GET_PRODUCTS = "/product/get.products.json";
			public static final String PRODUCT_GET_MAIN_PRODUCTS = "/product/get.products.main.json";
			public static final String PRODUCT_GET_PRODUCT = "/product/get.product.json";
			public static final String PRODUCT_GET_RECOMMEND_PRODUCTS = "/product/get.products.recommend.json";
			public static final String PRODUCT_GET_LATEST_PRODUCTS = "/product/get.products.latest.json";
			public static final String PRODUCT_CREATE = "/product/do.create.json";
			public static final String PRODUCT_UPDATE = "/product/update";

			public static final String PRODUCT_PARAM_ID = "id";
			public static final String PRODUCT_PARAM_CATEGORY = "group";
			public static final String PRODUCT_PARAM_NAME = "name";
			public static final String PRODUCT_PARAM_PAGE = "page";
			public static final String PRODUCT_PARAM_PERPAGE = "perpage";
		}

		public class Group {
			public static final String GET_GROUP_TREE = "/group/get.group.tree.json";
			public static final String GET_GROUP = "/group/get.group.json";
			public static final String GET_GROUPS = "/group/get.group.json";
			public static final String GET_BY_PARENT = "/group/get.group.byparent.json";
			public static final String GROUP_PARAM_ID = "id";
			public static final String UPDATE = "/group/update";
		}
	}

	public class Views {
		public static final String ABOUT = "about";
		public static final String GROUP = "group";
		public static final String ERROR404 = "error404";
		public static final String LOGIN = "login";
		public static final String MARKET_HOME = "market";
		public static final String MY_PAGE = "my";
		public static final String PRODUCT = "product";
		public static final String REGISTER = "reg";
		public static final String SEARCH = "search";
		public static final String UPLOAD = "upload";
		public static final String ADMIN = "admin_main";
		public static final String ADMIN_PRODUCTS = "admin_products";
		public static final String ADMIN_GROUPS = "admin_groups";
		public static final String NEW_PRODUCTS = "newprods";
		public static final String ADMIN_PRODUCT = "admin_product";
		public static final String ADMIN_GROUP = "admin_group";
	}
}
