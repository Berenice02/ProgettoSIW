package it.uniroma3.siw.progetto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import it.uniroma3.siw.progetto.services.FotografoServices;

@Controller
public class SystemController {
	
	@Autowired
	FotografoServices fotografo;
	
	@RequestMapping(value = "/")
	public String root(Model model) {
		model.addAttribute("fotografi", this.fotografo.primi10Fotografi());
		return "home";
	}
	
	@GetMapping(value = "/cerca")
	public String cerca(@ModelAttribute("nome") String nome, Model model) {
		model.addAttribute("fotografo", fotografo.fotografoPerNome(nome));
		return "fotografo";
	}

}
