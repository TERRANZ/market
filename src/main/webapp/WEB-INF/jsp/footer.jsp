<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</tr>
<!-- <tr> -->
<!-- 	<a href="http://www.warlog.ru/" target="_blank"><img border="0" -->
<!-- 		src="http://www.warlog.ru/counter/?i=1" alt="счетчик посещений" -->
<!-- 		title="счетчик посещений" /></a> -->
<!-- </tr> -->
</table>
</td>
</tr>
</table>


<script type="text/javascript">
	if (!(navigator.userAgent.indexOf('Opera') != -1)) {
		Nifty("div.topmenu_notselected,div.topmenu_selected", "top transparent");

		Nifty("td.topcorners", "tl transparent");
		var tt_layers = getElementsBySelector("td.topcorners");
		for ( var k = 0, len = tt_layers.length; k < len; k++) {
			tt_layers[k].parentNode.style.backgroundColor = "#e0e7ff";
		}
		Nifty("td.topcorners", "tr transparent");

		Nifty("td.bottomcorners", "bl transparent");
		var tt_layers = getElementsBySelector("td.bottomcorners");
		for ( var k = 0, len = tt_layers.length; k < len; k++) {
			tt_layers[k].parentNode.style.backgroundColor = "#e0e7ff";
		}
		Nifty("td.bottomcorners", "br transparent");
	}
</script>
<script type="text/javascript" src="resources/js/market.js"></script>
</center>
</body>
</html>