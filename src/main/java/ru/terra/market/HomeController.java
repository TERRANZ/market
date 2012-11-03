package ru.terra.market;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.market.constants.URLConstants;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController
{
	@RequestMapping(value = URLConstants.Pages.HOME, method = RequestMethod.GET)
	public String home(Locale locale, Model model)
	{
		return "redirect:/"+URLConstants.Views.MARKET_HOME;
	}

}
