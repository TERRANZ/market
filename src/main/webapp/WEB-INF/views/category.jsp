<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div class="center_content">
	<input type="hidden" id="category_id" value=${ categoryId } />
	<div class="center_title_bar">Товары в категории: ${ catname }</div>
	<div id="category_products"></div>
</div>
<script src="resources/js/category.js"></script>
<%@include file="/WEB-INF/jsp/footer.jsp"%>