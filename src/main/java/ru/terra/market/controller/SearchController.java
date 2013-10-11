package ru.terra.market.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.market.constants.URLConstants;

@Controller
public class SearchController {
	@RequestMapping(value = URLConstants.Pages.SEARCH, method = RequestMethod.GET)
	public String search(HttpServletRequest request, Locale locale, Model model) {
		String name = request.getParameter("name");
		if (name == null)
			return URLConstants.Views.ERROR404;
		else
			model.addAttribute("name", name);
		return URLConstants.Views.SEARCH;
	}
}
