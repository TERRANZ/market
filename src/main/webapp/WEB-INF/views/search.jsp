<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div class="center_content">
	<input type="hidden" id="searchname" value="${name}" />
	<div class="center_title_bar">Поиск: ${name}</div>
	<div id="searched"></div>
</div>
<!-- end of center content -->
<script src="resources/js/search.js"></script>
<%@include file="/WEB-INF/jsp/footer.jsp"%>