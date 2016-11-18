<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, user-scalable=no">
    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css"/>
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
    <title>Twoch saver</title>
</head>

<body>
<!-- Page: home  -->
<div id="home">
    <div role="main" class="ui-content">
        <a class="ui-btn" href="/list">Downloaded</a>
        <form action="start" method="get">
            <div class="ui-field-contain">
                <label for="txturl">URL</label>
                <input type="text" name="url" id="txturl">
            </div>
            <input type="submit" value="Start"/>
        </form>
    </div>
</div>
</body>
</html>
