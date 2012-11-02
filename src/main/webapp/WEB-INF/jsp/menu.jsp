<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<tr>
	<td valign="middle" width="220"><a href="/market/market"><img
			src="resources/images/companyname.gif" border="0"
			alt="Радиоэлектротовары"></a></td>
	<td valign="bottom" width="400">
		<table id="tabnav" border="0" cellspacing="0" cellpadding="0">
			<tr valign="top">
				<td><div id="menu_main" class="topmenu_selected">
						<a href="/market/market" class="menu">Главная</a>
					</div></td>
				<td>&nbsp;</td>
				<td><div id="menu_price" class="topmenu_notselected">
						<a href="/market/price" class="menu">Прайс-лист</a>
					</div></td>
				<td>&nbsp;</td>
				<td><div id="menu_about" class="topmenu_notselected">
						<a href="/market/about" class="menu"><nobr>О магазине</nobr></a>
					</div></td>
			</tr>
		</table>
	</td>
	<td valign="middle" align="right"
		style="background: #ffffff url(resources/images/gradientbg1.gif) repeat-y; background-position: right; height: 70px; width: 160px;"><a
		class="lightsmall"
		href="javascript:document.lang_form.new_language.value=0;document.lang_form.submit();">Русский</a>
		<font class="lightsmall">/</font> <a class="lightsmall"
		href="javascript:document.lang_form.new_language.value=1;document.lang_form.submit();">English</a>

		<form name="lang_form" method="post" action="/market/market">
			<input type="hidden" name="new_language">
		</form>

		<table cellspacing=0 cellpadding=1 border=0>

			<form action="/market/search" method=get>

				<tr>
					<td><input type="text" name="searchstring" size="15"
						style="color: #3E578F;" value="Поиск"
						onclick="this.value='';this.style.color='#000000';"></td>
					<td><nobr>
							<input type="image" border=0 src="resources/images/search.gif">
						</nobr></td>
				</tr>

			</form>
		</table></td>
</tr>