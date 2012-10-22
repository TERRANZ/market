<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="STYLESHEET" href="resources/css/main.css" type="text/css" />
<title>РадиоЭлектроТовары - главная</title>
</head>
<body>
	<input type="hidden" id="currentCategoryId" value="${categoryId}" />
	<input type="hidden" id="currentMenuId" value="${currentMenuId}" />
	<%@include file="/WEB-INF/jsp/jsinclude.jsp"%>
	<div id="header">
		<a href="/market" class="logo">РадиоЭлектроТовары</a> <a href="/market">Главная</a> <a href="/market/price">Прайс</a>
	</div>
	<div id="main_page">
		<div id="categories" style="float: left; width: 15%">
			<h3 align="center">Категории</h3>
			<div id="category-wrapper" align="center"></div>
		</div>
		<div id="content" align="center">
			<h1>тут контент</h1>
			<table id="main_page_prices" border="1"></table>
		</div>
	</div>
	<script type="text/javascript" src="resources/js/category.js"></script>
	<script type="text/javascript" src="resources/js/price.js"></script>
</body>
</html>