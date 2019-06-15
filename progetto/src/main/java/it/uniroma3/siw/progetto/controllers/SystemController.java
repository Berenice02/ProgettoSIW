package it.uniroma3.siw.progetto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SystemController {
	
	@RequestMapping(value = "/")
	public String root() {
		return "home";
	}

}
