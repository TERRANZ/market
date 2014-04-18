<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="left_content">
	<div class="title_box">Категории</div>

	<div class="left_menu" id="groups"></div>

	<div class="title_box">Новинки</div>
	<div class="title_box">Прайсы</div>
	<div class="border_box"></div>


	<div class="title_box">Поиск</div>
	<div class="border_box">
		<form action="/market/search" method="post">
			<input type="text" name="name" /> <input type="submit"
				value="Искать" />
		</form>
	</div>

	<div class="banner_adds"></div>


</div>
<!-- end of left content -->
