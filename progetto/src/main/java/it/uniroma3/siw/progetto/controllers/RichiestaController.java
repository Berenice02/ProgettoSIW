package it.uniroma3.siw.progetto.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.progetto.models.Richiesta;
import it.uniroma3.siw.progetto.services.RichiestaServices;
import it.uniroma3.siw.progetto.services.RichiestaValidator;

@Controller
public class RichiestaController {
	@Autowired
	RichiestaServices services;
	
	@Autowired
	RichiestaValidator validator;
	
	@RequestMapping(value = "/nuovaRichiesta")
	public String nuovaRichiesta(Model model) {
		model.addAttribute("richiesta", new Richiesta());
		return "richiesta";
	}
	
	@PostMapping(value = "/salvaRichiesta")
	public String salvaRichiesta(@Valid @ModelAttribute("richiesta") Richiesta richiesta, Model model, BindingResult br) {
		this.validator.validate(richiesta, br);
		if(!br.hasErrors()) {
			services.salvaRichiesta(richiesta);
			return "toolbar";
		}
		else
			return "richiesta";
	}

}
