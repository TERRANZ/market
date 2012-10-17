package ru.terra.market.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.market.constants.URLConstants;

@Controller
public class CatalogController
{

	@RequestMapping(value = URLConstants.Pages.CATALOG, method = RequestMethod.GET)
	public String catalog(Locale locale, Model model)
	{
		return URLConstants.Views.CATALOG;
	}
}
