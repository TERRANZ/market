$("#items").ready(loadItems());

function loadItems() {
	var groupId = $("#group_id").val();
	$.ajax({
		url : '/market/product/get.products.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		data : {
			group : groupId,
			all : true
		},
		success : function(data) {
			if (data.size != -1) {
				var htmlRet = "";
				$.each(data.data, function(i, product) {
					htmlRet += buildProductInfo(product);
				});
				$("#items").html(htmlRet);
			}
		}
	});
}

function buildProductInfo(product) {
	var comment = product.comment;
	if (comment == null)
		comment = product.name;

	var htmlRet = "";
	htmlRet += '<div class="col-md-3 col-sm-6 hero-feature">';
	htmlRet += '<div class="thumbnail">';
	if (product.photos.length > 0)
		htmlRet += '<img src="' + product.photos[0].path + '" width=800 heigth=500 alt="" title="" border="0" /></a>';

	else
		htmlRet += '<img src="/market/qr?product=' + product.id + '" width=800 heigth=500 alt=""/>';

	htmlRet += '<div class="caption">';
	htmlRet += '<h3>' + product.name + '</h3>';
	htmlRet += '<p>' + comment + '</p>';
	htmlRet += '<p>';
	htmlRet += '<a href="/market/product?id=' + product.id + '" class="btn btn-default">О товаре</a>';
	htmlRet += '</p>';
	htmlRet += '</div>';
	htmlRet += '</div>';
	htmlRet += '</div>';	
 
	return htmlRet;
}