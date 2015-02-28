<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/jsp/header.jsp"%>
<div class="center_content">
	<form action="/market/do.login" method="post">
		<label for="j_username">Логин</label> <input type="text"
			name="j_username" id="j_username" /> <br /> <label for="j_password">Пароль</label>
		<input type="password" name="j_password" id="j_password" /> <br /> <input
			type='checkbox' name='_spring_security_remember_me' /> Remember me
		on this computer. <br /> <input type="submit" value="Войти" />
	</form>
</div>
<script src="resources/js/login.js"></script>
<%@include file="/WEB-INF/jsp/footer.jsp"%>