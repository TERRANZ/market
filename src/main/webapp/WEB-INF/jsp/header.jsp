<%@page import="ru.terra.market.web.security.SessionHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=UTF-8" />
<title>РадиоЭлектроТовары</title>
<link rel="stylesheet" type="text/css" href="resources/css/style.css" />
<!--[if IE 6]>
<link rel="stylesheet" type="text/css" href="resources/css/iecss.css" />
<![endif]-->
<%@include file="/WEB-INF/jsp/jsinclude.jsp"%>
<script type="text/javascript" src="resources/js/boxOver.js"></script>
</head>
<body>

	<div id="main_container">

		<div id="header">

			<div class="top_right">

				<div class="languages">
<!-- 					<div class="lang_text">Languages:</div> -->
<!-- 					<a href="#" class="lang"><img src="resources/images/en.gif" alt="" -->
<!-- 						title="" border="0" /></a> <a href="#" class="lang"><img -->
<!-- 						src="resources/images/de.gif" alt="" title="" border="0" /></a> -->
				</div>

				<div class="big_banner">
					<a href="#"><img src="resources/images/banner728.jpg" alt="" title=""
						border="0" /></a>
				</div>

			</div>


			<div id="logo">
				<a href="/market/market"><img src="resources/images/logo.png" alt="" title=""
					border="0" width="182" height="85" /></a>
			</div>




		</div>

		<div id="main_content">

			<div id="menu_tab">
				<ul class="menu">
					<li><a href="/market/market" class="nav"> Главная </a></li>
					<li class="divider"></li>
<!-- 					<li><a href="#" class="nav">Товары</a></li> -->
<!-- 					<li class="divider"></li> -->
					<li><a href="#" onclick="loadLatest(); return false;" class="nav">Новинки</a></li>
					<li class="divider"></li>
					<% if (SessionHelper.isUserCurrentAuthorized()){ %>
					<li><a href="/market/my" class="nav">Мой аккаунт</a></li>
					<li class="divider"></li>
					<% }else{ %>
					<li><a href="/market/login" class="nav">Авторизация</a></li>
					<li class="divider"></li>
					<% } %>
<!-- 					<li><a href="#" class="nav">Доставка </a></li> -->
<!-- 					<li class="divider"></li> -->
					<li><a href="/market/about" class="nav">Связь с нами</a></li>

				</ul>

			</div>
			<!-- end of menu tab -->

			<div class="crumb_navigation">
				Навигация: <span class="current">Главная</span>

			</div>
<%@include file="/WEB-INF/jsp/left.jsp"%>    