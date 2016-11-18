package ru.terra.market.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(URLConstants.UI.MAIN)
public class UIController {
    @RequestMapping(URLConstants.UI.MAIN)
    public String main() {
        return URLConstants.View.MAIN;
    }
}
