package ru.terra.market.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.market.constants.ModelConstants;
import ru.terra.market.constants.URLConstants;

@Controller
public class MyController
{
	@RequestMapping(value = URLConstants.Pages.MY_PAGE, method = RequestMethod.GET)
	public String mhome(Locale locale, Model model)
	{
		return URLConstants.Views.MY_PAGE;
	}
}
