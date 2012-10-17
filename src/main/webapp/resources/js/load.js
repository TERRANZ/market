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
					newHtml += "<a href=/market/category?id=" + d.id + "> "
							+ d.name + "</a>";
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
				if (data.size == 0) {
					loadProducts(parentid);
				} else if (data.size != -1) {
					var newHtml = "";
					$.each(data.data, function(i, d) {
						newHtml += "<tr><td align=center>";
						newHtml += "<a href='/market/category?id=" + d.id
								+ "'> " + d.name + "</a>";
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
	var categoryWrapper = $("#category_with_count");
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