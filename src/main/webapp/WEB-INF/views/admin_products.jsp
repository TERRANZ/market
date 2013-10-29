<%@page import="ru.terra.market.constants.URLConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div class="center_content">

	<div class="oferta">
		<div class="oferta_details">
			<div class="oferta_title">Управление товарами</div>
			<div class="oferta_text">
			<a href="<%=URLConstants.Pages.NEW_PRODUCTS%>">Добавить товар</a>
			
			</div>
		</div>
	</div>

</div>
<!-- end of center content -->
<%@include file="/WEB-INF/jsp/footer.jsp"%>