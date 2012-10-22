package ru.terra.market.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.market.constants.ModelConstants;
import ru.terra.market.constants.URLConstants;

@Controller
public class MarketController
{
	@RequestMapping(value = URLConstants.Pages.MARKET_HOME, method = RequestMethod.GET)
	public String mhome(Locale locale, Model model)
	{
		model.addAttribute(ModelConstants.CURRENT_MENU_ID, 0);
		model.addAttribute(ModelConstants.CATEGORY_ID, 0);
		return URLConstants.Views.MARKET_HOME;
	}

	@RequestMapping(value = URLConstants.Pages.ABOUT, method = RequestMethod.GET)
	public String about(Locale locale, Model model)
	{
		model.addAttribute(ModelConstants.CURRENT_MENU_ID, 2);
		model.addAttribute(ModelConstants.CATEGORY_ID, 0);
		return URLConstants.Views.ABOUT;
	}
}