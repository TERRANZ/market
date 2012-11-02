<%@page import="ru.terra.market.web.security.SessionHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div id="content" align="center">
	<input type="hidden" id="productId" value="${id}" />
	<h1 id="prodname"></h1>
	<div id="product_wrapper">
		<div>
			<%
				if (SessionHelper.isUserCurrentAuthorized())
				{
			%>
			<div id="product_admin">
				<a href="/market/upload?product=${id}">Добавить фотографию</a>
			</div>
			<%
				}
			%>

		</div>
		<div id="product_info">
		<img align="right" src="/market/qr?product=${id}" width="200" height="200"></img>
			<div id="product_rating"></div>
			<table id="product_photos"></table>
		</div>


	</div>
</div>
<script type="text/javascript" src="resources/js/product.js"></script>
<%@include file="/WEB-INF/jsp/footer.jsp"%>