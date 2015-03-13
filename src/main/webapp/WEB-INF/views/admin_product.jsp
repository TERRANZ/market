<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="ru.terra.market.db.entity.Group"%>
<%@page import="java.util.List"%>
<%@page import="ru.terra.market.constants.URLConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div class="center_content">
	<script type="text/javascript">
function save(){	
	$.ajax({
		url : '/market/product/update',
		async : false,
		type : 'post',
		dataType : 'jsonp',
		data : {
			id : $("#pid").val(),
			name : $("#name").val(),
			mincount : $("#mincount").val(),
			barcode : $("#barcode").val(),
			qtype : $("#qtype").val(),
			priceIn : $("#priceIn").val(),
			priceOut : $("#priceOut").val(),
			rating : $("#rating").val(),
			parent : $("#parent").val(),
			comment : $("#comment").val()
		},
		success : function(data) {

		}
	});
}
</script>

	<div class="oferta_title">Группа</div>
	<input id="pid" type="hidden" value="${product.id}"/><br/>
	<p>Название</p><input id="name" value="${product.name}" /><br/>
	<p>Мин. количество</p><input id="mincount" value="${product.mincount}" /><br/>
	<p>Штрихкод</p><input id="barcode" value="${product.barcode}" /><br/>
	<p>Тип исчисления</p><input id="qtype" value="${product.qtype}" /><br/>
	<p>Цена приход</p><input id="priceIn" value="${product.priceIn}" /><br/>
	<p>Цена прод.</p><input id="priceOut" value="${product.priceOut}" /><br/>
	<p>Рейтинг</p><input id="rating" value="${product.rating}" />	<br/>
	<p>Группа</p><select id="parent">
		<c:forEach items="${groups}" var="g">
			<option <c:if test="${product.group.id == g.id}"> selected </c:if>
				value=<c:out value="${g.id}"/>>${g.name}</option>
		</c:forEach>
	</select><br/>
	<p>Коммент</p><input id="comment" value="${product.comment}" /><br/>
	<button onclick="save();" value="Сохранить" title="Сохранить">Сохранить</button>


</div>
<!-- end of center content -->
<%@include file="/WEB-INF/jsp/footer.jsp"%>