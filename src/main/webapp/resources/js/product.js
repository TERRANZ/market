$("#product_info").ready(loadProduct());


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
			$("#pname").html(product.name);			
			$("#pprice").html(product.price);			
			if (product.photos.length > 0) {
				$("#pphoto").attr("src", product.photos[0].path);
			} else {
				$("#pphoto").attr("src", '/market/qr?product=' + product.id);
			}
			$("#pcomment").html(product.comment);
		}
	});

}