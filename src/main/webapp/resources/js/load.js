function loadLeftCategories() {
	var categoryWrapper = $("#category-wrapper");
	$.ajax({
		url : '/market/category/get.category.tree.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		success : function(data) {
			if (data.size != -1) {
				var newHtml = "";
				$.each(data.data, function(i, d) {
					newHtml += "<a class='category_link' id='" + d.id + "' href=/market/category?id=" + d.id + "> " + d.name + "</a></br>";
				});
				categoryWrapper.html(newHtml);
			} else {
				alert("Ошибка при загрузке списка категорий");
			}
		}
	});
}
function loadCenterCategories() {
	var parentid = $("#currentCategoryId").val();
	var categoryWrapper = $("#category_with_count");
	if (parentid == "")
		loadLeftCategories();
	else
		$.ajax({
			url : '/market/category/get.category.tree.json',
			async : false,
			type : 'get',
			dataType : 'jsonp',
			data : {
				id : parentid
			},
			success : function(data) {
				if (data.size != -1) {
					loadProducts(parentid);
					var newHtml = "";
					$.each(data.data, function(i, d) {
						newHtml += "<tr><td align=center>";
						newHtml += "<a href='/market/category?id=" + d.id + "'> " + d.name + "</a>";
						newHtml += "</td>";
						newHtml += "<td>" + d.count + "</td>";
						newHtml += "</tr>";
					});
					categoryWrapper.html(newHtml);
				} else {
					alert("Ошибка при загрузке списка категорий");
				}
			}
		});
}

function loadProducts(category) {
	var categoryWrapper = $("#category_products");
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
				var newHtml = "";
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
				categoryWrapper.html(newHtml);
			}
		}
	});
}

function loadMainPagePrices() {
	var productsWrapper = $("#main_page_prices");
	$(".category_link").each(function(i, domEle) {
		$.ajax({
			url : '/market/product/get.products.json',
			async : false,
			type : 'get',
			dataType : 'jsonp',
			data : {
				category : domEle.id,
				limit : 3
			},
			success : function(data) {
				if (data.size != -1) {
					var newHtml = productsWrapper.html();
					newHtml += "<tr>";
					$.each(data.data, function(i, d) {
						newHtml += "<td align=center>";
						newHtml += "<td><a href='/market/product?id="+d.id+"'>";
						newHtml += d.name;
						newHtml += "</a></td>";
						newHtml += "<td>";
						newHtml += d.avail ? "Доступен" : "Не доступен";
						newHtml += "</td>";
					});
					newHtml += "</tr>";
					productsWrapper.html(newHtml);
				}
			}
		});
	});
}