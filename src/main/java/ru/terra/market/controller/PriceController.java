package ru.terra.market.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.market.constants.ModelConstants;
import ru.terra.market.constants.URLConstants;

@Controller
public class PriceController
{
	@RequestMapping(value = URLConstants.Pages.PRICE, method = RequestMethod.GET)
	public String price(Locale locale, Model model)
	{
		model.addAttribute(ModelConstants.CURRENT_MENU_ID, 1);
		model.addAttribute(ModelConstants.CATEGORY_ID, 0);
		return URLConstants.Views.PRICE;
	}

}
