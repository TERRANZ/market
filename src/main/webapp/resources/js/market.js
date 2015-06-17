$("#recommended").ready(loadRecommended());

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

function buildProductInfo(product) {
	var comment = product.comment;
	if (comment == null)
		comment = product.name;

	var htmlRet = "";
	htmlRet += '<div class="col-sm-4 col-lg-4 col-md-4">';
	htmlRet += '<div class="thumbnail">';
	if (product.photos.length > 0)
		htmlRet += '<img src="' + product.photos[0].path + '" width=320 heigth=150 alt="" title="" border="0" /></a>';

	else
		htmlRet += '<img src="/market/qr?product=' + product.id + '" width=320 heigth=150 alt=""/>';

	htmlRet += '<div class="caption">';
	htmlRet += '<h4 class="pull-right">₽' + product.price + '</h4>';
	htmlRet += '<h4>';
	htmlRet += '<a href="/market/product?id=' + product.id + '">' + comment + '</a>';
	htmlRet += '</h4>';
	htmlRet += '<p>' + comment + '</p>';
	htmlRet += '</div>';
	htmlRet += '<div class="ratings">';
	htmlRet += '<p class="pull-right">12 reviews</p>';
	htmlRet += '<p>';
	htmlRet += '<span class="glyphicon glyphicon-star"></span>';
	htmlRet += '<span class="glyphicon glyphicon-star"></span>';
	htmlRet += '<span class="glyphicon glyphicon-star"></span>';
	htmlRet += '<span class="glyphicon glyphicon-star"></span>';
	htmlRet += '<span class="glyphicon glyphicon-star-empty"></span>';
	htmlRet += '</p>';
	htmlRet += '</div>';
	htmlRet += '</div>';
	htmlRet += '</div>';
	return htmlRet;
}