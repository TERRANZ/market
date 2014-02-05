<%@page import="ru.terra.market.constants.URLConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div class="center_content">
	<script src="resources/js/admin.js"></script>
	<div class="oferta">
		<div class="oferta_title">Новые товары</div>
		<div class="oferta_text">
			<label>Категория</label>
			<div id='group_wrapper'></div>
			</br>
			<script type="text/javascript" class="source below">
				loadGroups();
			</script>
			<label>Список товаров, заголовок и имя разделены табом</label></br>
			<textarea id="products" rows="5" cols="40"></textarea>
			</br> <input type="button" value="создать" onclick="createProduct();" />
		</div>

	</div>

</div>
<!-- end of center content -->
<%@include file="/WEB-INF/jsp/footer.jsp"%>