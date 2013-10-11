$("#category_products").ready(loadCategoryProducts(1));
$('#perpage').on('change', function() {
	loadCategoryProducts(1);
});