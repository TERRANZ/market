function createProduct() {
	var prods = $("#products").val();
	var group = $("#group").val();
	var products = prods.toString().split(/\n/);
	for (prod in products) {
		var splitted = products[prod].split(/\t/);
		var t = splitted[0];
		var c = splitted[1];
		$.ajax({
			url : '/market/product/do.create.json',
			async : false,
			type : 'post',
			dataType : 'jsonp',
			data : {
				title : t,
				comment : c,
				group : group
			},
			success : function(data) {

			}
		});
	}
}

function loadCategories() {
	$.ajax({
		url : '/market/group/get.group.json',
		async : false,
		type : 'get',
		dataType : 'jsonp',
		success : function(data) {
			var html = "<select id='group'>";
			$.each(data.data, function(i, c) {
				html += "<option value=" + c.id + ">" + c.name + "</option>";
			});
			html += "</select>";
			$("#group_wrapper").html(html);
		}
	});
}