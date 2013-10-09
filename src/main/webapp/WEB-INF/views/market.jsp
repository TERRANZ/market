<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div class="center_content">

	<div class="oferta">
		<img src="resources/images/p1.jpg" width="165" height="113" border="0"
			class="oferta_img" alt="" title="" />

		<div class="oferta_details">
			<div class="oferta_title">Радиэлетротовары</div>
			<div class="oferta_text">Магазин расположен в городе Новокуйбышевске</div>			
		</div>
	</div>


	<div class="center_title_bar">Последние добавления</div>
	<div id="latest_adds">

	</div>

	<div class="center_title_bar" >Рекомендованные
		товары</div>
	<div id="recommended"></div>


</div>
<!-- end of center content -->
<script src="resources/js/market.js"></script>
<%@include file="/WEB-INF/jsp/footer.jsp"%>