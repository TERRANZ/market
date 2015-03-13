<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="ru.terra.market.db.entity.Group"%>
<%@page import="java.util.List"%>
<%@page import="ru.terra.market.constants.URLConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div class="center_content">


	<div class="oferta_title">Управление группами</div>
	<a href="<%=URLConstants.Pages.ADMIN_GROUP%>?id=-1">Создать</a>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Наименование</th>
				<th>Родитель</th>
				<th>Редактировать</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${groups}" var="group">
				<tr>
					<td><c:out value="${group.id}" /></td>
					<td><c:out value="${group.name}" /></td>
					<td><c:out value="${group.parent}" /></td>
					<td><a
						href="<%=URLConstants.Pages.ADMIN_GROUP%>?id=${group.id}">Редактировать</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>
<!-- end of center content -->
<%@include file="/WEB-INF/jsp/footer.jsp"%>