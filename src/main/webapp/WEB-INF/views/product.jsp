<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div class="center_content">
	<input type="hidden" id="productid" value="${id}" />
	<div class="center_title_bar"></div>

	<div class="prod_box_big">

		<div class="center_prod_box_big">

			<div class="product_img_big">
			<img id="product_img_main" src="" alt="" title="" width="100" height="100" border="0" />
				<div class="thumbs">					
				</div>
			</div>
			<div class="details_big_box">
				<div class="product_title_big"></div>
				<div class="specifications">
					Доступность: <span class="blue" id="avail"></span><br /> Описание: <span
						class="blue" id="details_description"> </span><br />
				</div>
				<div class="prod_price_big">
					<span class="price"></span>
				</div>
			</div>
		</div>

	</div>


</div>
<!-- end of center content -->
<script src="resources/js/product.js"></script>
<%@include file="/WEB-INF/jsp/footer.jsp"%>