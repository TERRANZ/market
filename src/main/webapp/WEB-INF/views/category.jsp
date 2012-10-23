<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div id="content" align="center">
	<h1>Категория</h1>
	<h6>Вложенные категории</h6>
	<div id="category_wrapper" border="1">
		<h6>Отсутствуют</h6>
	</div>
	<h6>Товары в категории</h6>
	<div id="category_products_wrapper" border="1">
		<h6>Отсутствуют</h6>
	</div>
</div>
<script type="text/javascript">
$("#categories").ready(loadLeftCategories(${categoryId}));
$("#category_wrapper").ready(loadCenterCategories(${categoryId}));
</script>
<script type="text/javascript" src="resources/js/category.js"></script>
<%@include file="/WEB-INF/jsp/footer.jsp"%>