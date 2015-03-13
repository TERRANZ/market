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
		url : '/market/group/update',
		async : false,
		type : 'post',
		dataType : 'jsonp',
		data : {
			id : $("#gid").val(),
			name : $("#name").val(),
			parent : $("#parent").val()
		},
		success : function(data) {

		}
	});
}
</script>

	<div class="oferta_title">Группа</div>
	<input id="gid" type="hidden" value="${group.id}"/>
	<input id="name" value="${group.name}" /> <select id="parent">
		<c:forEach items="${groups}" var="g">
			<option <c:if test="${group.parent == g.id}"> selected </c:if>
				value=<c:out value="${g.id}"/>>${g.name}</option>
		</c:forEach>
	</select>
	<button onclick="save();" value="Сохранить" title="Сохранить">Сохранить</button>


</div>
<!-- end of center content -->
<%@include file="/WEB-INF/jsp/footer.jsp"%>