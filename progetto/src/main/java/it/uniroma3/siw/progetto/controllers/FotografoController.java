package it.uniroma3.siw.progetto.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.progetto.models.Fotografo;
import it.uniroma3.siw.progetto.services.FotografoServices;
import it.uniroma3.siw.progetto.services.FotografoValidator;

@Controller
public class FotografoController {
	@Autowired
	FotografoServices services;
	
	@Autowired
	FotografoValidator validator;
	
	@RequestMapping(value = "/nuovoFotografo")
	public String nuovoFotografo(Model model) {
		model.addAttribute("fotografo", new Fotografo());
		return "fotografoForm";
	}
	
	@PostMapping(value = "/salvaFotografo")
	public String salvaFotografo(@Valid @ModelAttribute("fotografo") Fotografo fotografo, Model model, BindingResult br) {
		this.validator.validate(fotografo, br);
		if(!br.hasErrors()) {
			services.salvaFotografo(fotografo);
			return "home";
		}
		else
			return "fotografoForm";
	}
	
	@GetMapping(value = "/fotografi")
	public String getRichieste(Model model) {
		model.addAttribute("fotografi", this.services.primi10Fotografi());
		return "fotografi";
	}
	
	@RequestMapping(value = "/fotografo/{id}")
	public String getFotografo(@PathVariable("id") Long id, Model model) {
		if(id!=null) {
			model.addAttribute("fotografo", this.services.fotografoPerId(id));
			return "fotografo";
		}
		else {
			model.addAttribute("fotografi", this.services.primi10Fotografi());
			return "fotografi";
		}
	}

}
