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
			all : false,
			perpage : 3,
			page : 1
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
			all : false,
			perpage : 3,
			page : 1
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
		success : function(product) {
			$(".center_title_bar").html(product.name);
			$(".product_title_big").html(product.name);
			$(".price").html(product.price);
			$("#avail").html(product.avail ? "В продаже" : "Нет в продаже");
			if (product.photos.length > 0) {
				$("#product_img_main").attr("src", product.photos[0].path);
			} else {
				$("#product_img_main").attr("src", '/market/qr?product=' + product.id);
			}
			$("#details_description").html(product.comment);
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
	htmlRet += '<span class="price">' + product.price + ' руб.</span>';
	htmlRet += '</div>';
	htmlRet += '</div>';
	htmlRet += '<div class="prod_details_tab">';
	htmlRet += '<a href="/market/product?id=' + product.id + '" class="prod_details">Подробности...</a>';
	htmlRet += '</div>';
	htmlRet += '</div>';
	// htmlRet += '</div>';
	return htmlRet;
}

function loadCategoryProducts(page) {
	var catId = $("#category_id").val();
	// var page = $("#page").text();
	var perpage = $("#perpage").val();
	$.ajax({
		url : '/market/product/get.products.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		data : {
			category : catId,
			page : page - 1,
			perpage : perpage
		},
		success : function(data) {
			if (data.size != -1) {
				var htmlRet = "";
				$.each(data.data, function(i, product) {
					htmlRet += buildProductInfo(product);
				});
				$("#category_products").html(htmlRet);
				$("#paging").pagination({
					items : data.full,
					itemsOnPage : perpage,
					cssStyle : 'compact-theme',
					currentPage : page,
					prevText: '<=',
					nextText: '=>',
					onPageClick : function(pageNumber, event) {
						loadCategoryProducts(pageNumber);
					}
				});
			}
		}
	});

}

function loadSearch() {
	var sname = $("#searchname").val();
	$.ajax({
		url : '/market/product/get.products.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		data : {
			name : sname
		},
		success : function(data) {
			if (data.size != -1) {
				var htmlRet = "";
				$.each(data.data, function(i, product) {
					htmlRet += buildProductInfo(product);
				});
				$("#searched").html(htmlRet);
			}
		}
	});
}

function loadLatest() {
	var newHtml = '<div class="center_title_bar">Последние добавления</div>';
	newHtml += '<div id="latest_adds"></div>';
	$(".center_content").html(newHtml);
	loadLatestAdds();
}