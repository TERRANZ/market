<%@page import="ru.terra.market.dto.photo.PhotoDTO"%>
<%@page import="ru.terra.market.dto.product.ProductDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div class="center_content">
	<input type="hidden" id="searchname" value="${name}" />
	<div class="center_title_bar">Поиск: ${name}</div>
	<div id="searched">
		<%
			for (ProductDTO product : ((List<ProductDTO>) request.getAttribute("result"))) {
		%>

		<div class="prod_box">
			<div class="center_prod_box">
				<div class="product_title">
					<a href="/market/product?id=<%=product.id%>"> <%=product.name%>
					</a>
					<%
						if (product.comment != null){
					%>
					<p style="font-size: 9px;"><%=product.comment%>
					</p>
					<%
						}
					%>
				</div>
				<%
					if (product.photos.size() > 0) {
						for (PhotoDTO photo : product.photos) {
				%>
				<div class="product_img">
					<a href="/market/product?id=<%=product.id%>"><img
						src="<%=photo.path%>" width=100 heigth=100 alt="" title=""
						border="0" /></a>
				</div>
				<% 
					}
					} else {
				%>
				<div class="product_img">
					<a href="/market/product?id=<%=product.id%> + '"><img
						src="/market/qr?product=<%=product.id%>" width=100 heigth=100
						alt="" title="" border="0" /></a>
				</div>
				<%
					}
				%>
				<div class="prod_price">
					<span class="price"><%=product.price%> руб.</span>

				</div>
			</div>
			<div class="prod_details_tab">
				<a href="/market/product?id=<%=product.id%>" class="prod_details">Подробности...</a>
			</div>
		</div>

		<%
			}
		%>
	</div>
</div>
<!-- end of center content -->
<%@include file="/WEB-INF/jsp/footer.jsp"%>