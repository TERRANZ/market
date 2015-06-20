<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Радиоэлектротовары</title>

<!-- Bootstrap Core CSS -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="resources/css/shop-item.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
	<input type="hidden" id="productid" value="${id}" />
	<!-- Navigation -->
	<%@include file="/WEB-INF/jsp/navbar.jsp"%>

	<!-- Page Content -->
	<div class="container">

		<div class="row">

			<%@include file="/WEB-INF/jsp/groups.jsp"%>

			<div class="col-md-9" id="product_info">

				<div class="thumbnail">
					<img class="img-responsive" id="pphoto" src="http://placehold.it/800x300" alt="" width="800" height="300">
					<div class="caption-full">
						<h4 class="pull-right" id="pprice"></h4>
						<h4>
							<p id="pname">Product Name</p>
						</h4>					
						<p id="pcomment">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
							sed do eiusmod tempor incididunt ut labore et dolore magna
							aliqua. Ut enim ad minim veniam, quis nostrud exercitation
							ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis
							aute irure dolor in reprehenderit in voluptate velit esse cillum
							dolore eu fugiat nulla pariatur. Excepteur sint occaecat
							cupidatat non proident, sunt in culpa qui officia deserunt mollit
							anim id est laborum</p>
					</div>
					<div class="ratings">
						<p class="pull-right">0 reviews</p>
						<p>
							<span class="glyphicon glyphicon-star"></span> <span
								class="glyphicon glyphicon-star"></span> <span
								class="glyphicon glyphicon-star"></span> <span
								class="glyphicon glyphicon-star"></span> <span
								class="glyphicon glyphicon-star-empty"></span> 4.0 stars
						</p>
					</div>
				</div>

				<div class="well">

				</div>

			</div>

		</div>

	</div>
	<!-- /.container -->

	<div class="container">

		<hr>

		<!-- Footer -->
		<footer>
			<div class="row">
				<div class="col-lg-12">
					<p>Copyright &copy; Радиоэлектротовары 2015</p>
				</div>
			</div>
		</footer>

	</div>
	<!-- /.container -->

	<%@include file="/WEB-INF/jsp/js.jsp"%>
	
	<script src="resources/js/product.js"></script>

</body>

</html>
