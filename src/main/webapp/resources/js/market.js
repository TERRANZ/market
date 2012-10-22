$("#main_table").ready(marketLoad());

function marketLoad() {
	loadLeftCategories();
	setMenu();
}

function setMenu() {
	var currMenu = $("#currentMenuId").val();
	switch (currMenu) {
		case "0" :
			$("#menu_main").removeClass("topmenu_notselected").addClass("topmenu_selected");
			$("#menu_price").removeClass("topmenu_selected").addClass("topmenu_notselected");
			$("#menu_about").removeClass("topmenu_selected").addClass("topmenu_notselected");
			break;
		case "1" :
			$("#menu_main").removeClass("topmenu_selected").addClass("topmenu_notselected");
			$("#menu_price").removeClass("topmenu_notselected").addClass("topmenu_selected");
			$("#menu_about").removeClass("topmenu_selected").addClass("topmenu_notselected");
			break;
		case "2" :
			$("#menu_main").removeClass("topmenu_selected").addClass("topmenu_notselected");
			$("#menu_price").removeClass("topmenu_selected").addClass("topmenu_notselected");
			$("#menu_about").removeClass("topmenu_notselected").addClass("topmenu_selected");
			break;

		default :
			break;
	}
}