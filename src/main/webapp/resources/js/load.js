function loadLeftCategories() {
	var categoryWrapper = $("#categories");
	$.ajax({
		url : '/market/category/get.category.tree.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		success : function(data) {
			var newHtml = "";
			newHtml += buildCategoryTree(data, 0);
			categoryWrapper.html(newHtml);
		}
	});
}

function buildCategoryTree(categoryTree, level) {
	var newHtml = "";
	var id = categoryTree.category.id;
	var padding = 10 * level;
	newHtml += "<li class='odd'>";
	newHtml += "<a style='padding-left: " + padding + "px;' class='category_link' id='" + id + "'href=/market/category?id=" + id + "> "
			+ categoryTree.category.name + "</a>";
	newHtml += "</li>";
	if (categoryTree.hasChilds) {
		var newLevel = level + 1;
		$.each(categoryTree.childs, function(i, d) {
			newHtml += buildCategoryTree(d, newLevel);
		});
	}
	return newHtml;
}

function loadLatestAdds() {
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
				var htmlRet = "";
				$.each(data.data, function(i, product) {
					htmlRet += buildProductInfo(product);
				});
				$("#latest_adds").html(htmlRet);
			}
		}
	});

}

function loadRecommended() {
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
				var htmlRet = "";
				$.each(data.data, function(i, product) {
					htmlRet += buildProductInfo(product);
				});
				$("#recommended").html(htmlRet);
			}
		}
	});
}

function loadProduct() {
	var id = $("#productid").val();
	$.ajax({
		url : '/market/product/get.product.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		data : {
			id : id
		},
		success : function(data) {
			$("#center_title_bar").html(data.name);
			$("#product_title_big").html(data.name);
			$(".price").html(data.price);
			if (data.photos.length > 0) {
			} else {
				$("#product_img_main").attr("src", '/market/qr?product=' + data.id);
			}
		}
	});

}

function buildProductInfo(product) {
	var htmlRet = "";
	htmlRet += '<div class="prod_box">';
	htmlRet += '<div class="center_prod_box">';
	htmlRet += '<div class="product_title">';
	htmlRet += '<a href="/market/product?id=' + product.id + '">' + product.name + '</a>';
	htmlRet += '</div>';
	if (product.photos.length > 0) {
		$.each(product.photos, function(i, photo) {
			htmlRet += '<div class="product_img">';
			htmlRet += '<a href="/market/product?id=' + product.id + '"><img src="' + photo.path
					+ '" width=200 heigth=200 alt="" title="" border="0" /></a>';
			htmlRet += '</div>';
		});
	} else {
		htmlRet += '<div class="product_img">';
		htmlRet += '<a href="/market/product?id=' + product.id + '"><img src="/market/qr?product=' + product.id
				+ '" width=100 heigth=100 alt="" title="" border="0" /></a>';
		htmlRet += '</div>';
	}
	htmlRet += '<div class="prod_price">';
	htmlRet += '<span class="price">' + product.price + '</span>';
	htmlRet += '</div>';
	htmlRet += '</div>';
	htmlRet += '<div class="prod_details_tab">';
	htmlRet += '<a href="/market/product?id=' + product.id + '" class="prod_details">Подробности...</a>';
	htmlRet += '</div>';
	htmlRet += '</div>';
	// htmlRet += '</div>';
	return htmlRet;
}