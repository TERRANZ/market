<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="ru.terra.market.db.entity.Group"%>
<%@page import="java.util.List"%>
<%@page import="ru.terra.market.constants.URLConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div class="center_content">


	<div class="oferta_title">Управление товарами</div>
	<a href="<%=URLConstants.Pages.ADMIN_PRODUCT%>?id=-1">Создать</a>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Наименование</th>
				<th>Мин. количество</th>
				<th>Штрихкод</th>
				<th>Исчисление</th>
				<th>Цена приход</th>
				<th>Цена прод.</th>
				<th>Рейтинг</th>
				<th>Группа</th>
				<th>Комментарий</th>
				<th>Доступно</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${prods}" var="p">
				<tr>
					<td><c:out value="${p.id}"/></td>
					<td><c:out value="${p.name}"/></td>
					<td><c:out value="${p.mincount}"/></td>
					<td><c:out value="${p.barcode}"/></td>
					<td><c:out value="${p.qtype}"/></td>
					<td><c:out value="${p.priceIn}"/></td>
					<td><c:out value="${p.priceOut}"/></td>
					<td><c:out value="${p.rating}"/></td>
					<td><c:out value="${p.group.name}"/></td>
					<td><c:out value="${p.comment}"/></td>
					<td><% if (Boolean.parseBoolean("${p.avail}")) %>Доступен<% else %>Недоступен</td>
					<td><a
						href="<%=URLConstants.Pages.ADMIN_PRODUCT%>?id=${p.id}">Редактировать</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>
<!-- end of center content -->
<%@include file="/WEB-INF/jsp/footer.jsp"%>