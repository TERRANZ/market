<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>Добавление фотографий</title>
</head>
<body>
	<form:form modelAttribute="uploadDTO" method="post"
		enctype="multipart/form-data">
		<fieldset>
			<legend>Новая фотография</legend>

			<p>
				<form:label for="name" path="name">Имя</form:label>
				<br />
				<form:input path="name" />
			</p>

			<p>
				<form:label for="fileData" path="fileData">Файл</form:label>
				<br />
				<form:input path="fileData" type="file" />
			</p>
			<form:hidden path="targetId" />
			<p>
				<input type="submit" />
			</p>

		</fieldset>
	</form:form>
</body>
</html>