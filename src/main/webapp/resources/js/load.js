function loadLeftGroups() {
	var groupWrapper = $("#groups");
	$.ajax({
		url : '/market/group/get.group.tree.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		success : function(data) {
			var newHtml = "";
			newHtml += buildGroupTree(data, 0);
			groupWrapper.html(newHtml);
			var prepared = processTreeJson(true, data);
			groupWrapper.dynatree({
				children : prepared,
				onActivate : function(node) {
					if (!node.data.isFolder)
						var url = document.URL;
					if (url.indexOf("group") < 0)
						window.location.href = "/market/group?id=" + node.data.key;
					else {
						$("#group_id").val(node.data.key);
						$("#groupname").text(node.data.title);
						loadGroupProducts(1);
						window.history.pushState('group' + node.data.key, 'Категория ' + node.data.title, "/market/group?id=" + node.data.key);
					}
				},
			});
		}
	});
}

function processTreeJson(first, groupTree) {
	var children = [];
	var data = {
		title : groupTree.group.name,
		isFolder : groupTree.hasChilds,
		key : groupTree.group.id,
		children : []
	};
	children.push(data);
	if (groupTree.hasChilds)
		for ( var i = 0; i < groupTree.childs.length; i++) {
			data.children.push(processTreeJson(false, groupTree.childs[i]));
		}
	if (first)
		return children;
	return data;
}

function buildGroupTree(groupTree, level) {
	var newHtml = "<ul class='folder'>";
	var id = groupTree.group.id;
	var padding = 10 * level;
	newHtml += "<li class='odd'>" + groupTree.group.name;
	if (groupTree.hasChilds) {
		var newLevel = level + 1;
		$.each(groupTree.childs, function(i, d) {
			newHtml += buildGroupTree(d, newLevel);
		});
	}
	newHtml += "</ul>";
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
	var comment = product.comment;
	if (comment == null)
		comment = product.name;
	htmlRet += '<a href="/market/product?id=' + product.id + '">' + comment + '</a>';
	if (product.name != null)
		htmlRet += '<p style="font-size:9px;">' + product.name + "</p>";
	htmlRet += '</div>';
	if (product.photos.length > 0) {
		$.each(product.photos, function(i, photo) {
			htmlRet += '<div class="product_img">';
			htmlRet += '<a href="/market/product?id=' + product.id + '"><img src="' + photo.path
					+ '" width=100 heigth=100 alt="" title="" border="0" /></a>';
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

function loadGroupProducts(page) {
	var groupId = $("#group_id").val();
	// var page = $("#page").text();
	var perpage = $("#perpage").val();
	$.ajax({
		url : '/market/product/get.products.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		data : {
			group : groupId,
			page : page - 1,
			perpage : perpage
		},
		success : function(data) {
			if (data.size != -1) {
				var htmlRet = "";
				$.each(data.data, function(i, product) {
					htmlRet += buildProductInfo(product);
				});
				$("#group_products").html(htmlRet);
				$("#paging").pagination({
					items : data.full,
					itemsOnPage : perpage,
					cssStyle : 'compact-theme',
					currentPage : page,
					prevText : '<=',
					nextText : '=>',
					onPageClick : function(pageNumber, event) {
						loadGroupProducts(pageNumber);
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