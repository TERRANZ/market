<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="STYLESHEET" href="resources/css/main.css" type="text/css" />
<title>РадиоЭлектроТовары - главная</title>
</head>
<body>
	<input type="hidden" id="currentCategoryId" value="${categoryId}" />
	<input type="hidden" id="currentMenuId" value="${currentMenuId}" />
	<%@include file="/WEB-INF/jsp/jsinclude.jsp"%>
	<div id="header">
		<table align="center">
			<tr>
				<td><a href="/market" class="logo"><img src="resources/images/companyname.gif" /></a></td>
				<td><a clas="topmenu_notselected" href="/market">Главная</a></td>
				<td><a clas="topmenu_selected" href="/market/price">Прайс</a></td>
			</tr>
		</table>
	</div>
	<div id="main_page">
		<%@include file="/WEB-INF/jsp/left.jsp"%>