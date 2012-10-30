<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div id="content" align="center">
	<input type="hidden" id="productId" value="${id}" />
	<h1 id="prodname"></h1>
	<div id="product_wrapper">
		<div id="product_rating"></div>
		<div id="product_info"></div>
		<div id="product_photos"></div>
	</div>
</div>
<script type="text/javascript" src="resources/js/product.js"></script>
<%@include file="/WEB-INF/jsp/footer.jsp"%>