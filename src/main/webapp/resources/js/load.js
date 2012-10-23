function loadLeftCategories(selectedId) {
	var categoryWrapper = $("#category-wrapper");
	$.ajax({
		url : '/market/category/get.category.tree.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		success : function(data) {
			if (data.size != -1) {
				var newHtml = "<table align='center'>";
				$.each(data.data, function(i, d) {
					var id = d.id;
					if (selectedId != "undefinded" && selectedId == id)
						newHtml += "<tr class='category_selected' id='left_category_id" + id + "'><td>";
					else
						newHtml += "<tr id='left_category_id" + id + "'><td>";
					newHtml += "<a class='category_link' id='" + id + "' href=/market/category?id=" + id + "> " + d.name + "</a>";
					newHtml += "</td></tr>";
				});
				newHtml += "</table>";
				categoryWrapper.html(newHtml);
			} else {
				alert("Ошибка при загрузке списка категорий");
			}
		}
	});
}

function loadCenterCategories(parentId) {
	var categoryWrapper = $("#category_wrapper");
	$.ajax({
		url : '/market/category/get.category.tree.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		data : {
			id : parentId
		},
		success : function(data) {
			if (data.size != -1) {
				loadProducts(parentId);
				var newHtml = "<table>";
				$.each(data.data, function(i, d) {
					newHtml += "<tr><td align=center>";
					newHtml += "<a href='/market/category?id=" + d.id + "'> " + d.name + "</a>";
					newHtml += "</td>";
					newHtml += "<td>" + d.count + "</td>";
					newHtml += "</tr>";
				});
				newHtml += "</table>";
				categoryWrapper.html(newHtml);
			} else {
				alert("Ошибка при загрузке списка категорий");
			}
		}
	});
}

function loadProducts(category) {
	var categoryWrapper = $("#category_products_wrapper");
	$.ajax({
		url : '/market/product/get.products.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		data : {
			category : category
		},
		success : function(data) {
			if (data.size != -1) {
				var newHtml = "<table>";
				$.each(data.data, function(i, d) {
					newHtml += "<tr><td align=center>";
					newHtml += "<td>";
					newHtml += d.name;
					newHtml += "</td>";
					newHtml += "<td>";
					newHtml += d.avail ? "Доступен" : "Не доступен";
					newHtml += "</td>";
					newHtml += "</tr>";
				});
				newHtml += "</table>";
				categoryWrapper.html(newHtml);
			}
		}
	});
}

function loadMainPagePrices() {
	var newHtml = "";
	var productsWrapper = $("#main_page_prices");
	$.ajax({
		url : '/market/product/get.products.main.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		data : {
			limit : 3
		},
		success : function(data) {
			if (data.size != -1) {
				var j = 0;
				$.each(data.data, function(i, d) {
					if (j == 0)
						newHtml += "<tr>";
					j++;
					newHtml += "<td align=center>";
					newHtml += "<td><a href='/market/product?id=" + d.id + "'>";
					newHtml += d.name;
					newHtml += "</a></td>";
					newHtml += "<td>";
					newHtml += d.avail ? "Доступен" : "Не доступен";
					newHtml += "</td>";
					if (j == 3) {
						newHtml += "</tr>";
						j = 0;
					}
				});
			}
		}
	});
	productsWrapper.html(newHtml);
}