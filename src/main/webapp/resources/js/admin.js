function createProduct() {
	var prods = $("#products").val();
	var category = $("#category").val();
	var products = prods.toString().split(/\n/);
	// alert(products);
	for (prod in products) {
		var splitted = products[prod].split(/\t/);
		var t = splitted[0];
		var c = splitted[1];
		// alert("group = " + g + " product = " + p);
		$.ajax({
			url : '/market/product/do.create.json',
			async : false,
			type : 'post',
			dataType : 'jsonp',
			data : {
				title : t,
				comment : c,
				category : category
			},
			success : function(data) {

			}
		});
	}
}

function loadCategories() {
	$.ajax({
		url : '/market/category/get.categories.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		success : function(data) {
			var html = "<select id='category'>";
			$.each(data.data, function(i, c) {
				html += "<option value=" + c.id + ">" + c.name + "</option>";
			});
			html += "</select>";
			$("#category_wrapper").html(html);
		}
	});
}