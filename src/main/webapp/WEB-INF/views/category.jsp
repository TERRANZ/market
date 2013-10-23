<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<script type="text/javascript" src="resources/js/jquery.simplePagination.js"></script>
<link type="text/css" rel="stylesheet" href="resources/css/simplePagination.css"/>
<div class="center_content">
	<input type="hidden" id="category_id" value=${ categoryId } />
	<div class="center_title_bar">
		Товары в категории: <span id="catname"> ${ catname }</span>
		На странице 
		<select id="perpage">
			<option>3</option>
			<option>6</option>
			<option>12</option>
			<option>24</option>
		</select>		
	</div>
	<div id="category_products"></div>
	<div class="center_content" id="paging" ></div>
</div>
<script src="resources/js/category.js"></script>
<%@include file="/WEB-INF/jsp/footer.jsp"%>