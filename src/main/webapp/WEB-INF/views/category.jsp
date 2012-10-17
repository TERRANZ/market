<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<td width="100%" align="left" valign="top" style="padding: 10px;" colspan="2">Добро пожаловать в наш каталог товаров магазина Радиоэлектротовары.
	<center>
		<table border=0 cellspacing=0 cellpadding=10>
		</table>
	</center>
	<h1 align="center">
		<a name="catalog">Категория</a>
	</h1> <br>
	<h1 align="center">
		<a id="catname"></a>
	</h1>
	<p>
	<table width=100% border=0 cellpadding=5 id="category_with_count">
	</table>
</td>
<script type="text/javascript" src="resources/js/category.js"></script>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>