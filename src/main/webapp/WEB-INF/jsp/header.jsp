<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>

<head>

<link rel=STYLESHEET href="resources/css/style1.css" type="text/css">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Радиоэлектротовары</title>

<meta name="Title" content="Радиоэлектротовары">
<meta name="Description" content="Радиоэлектротовары, powered by Shop-Script">
<meta name="KeyWords" content="Радиоэлектротовары, powered by Shop-Script">

</head>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0">

	<input type="hidden" id="currentCategoryId" value="${categoryId}" />
	<input type="hidden" id="currentMenuId" value="${currentMenuId}" />
	<script type="text/javascript" src="resources/images/niftycube.js"></script>
	<%@include file="/WEB-INF/jsp/jsinclude.jsp"%>

	<center>
		<table width="780" border="0" cellspacing="0" cellpadding="0" id="main_table">
			<tr>
				<td bgcolor="white"><table width="100%" border="0" cellpadding="0" cellspacing="0">
						<%@include file="/WEB-INF/jsp/menu.jsp"%>
						
						<tr>
							<td bgcolor="white" height="6" align="right"><img src="resources/images/gradient-dark-strip.gif"></td>
							<td bgcolor="#203560" colspan="2" height="6"></td>
						</tr>
						<tr>
							<td width="220" valign="top" align="right"><%@ include file="/WEB-INF/jsp/left.jsp"%></td>
						