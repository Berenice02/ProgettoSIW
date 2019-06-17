package it.uniroma3.siw.progetto.controllers;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.progetto.models.Fotografo;
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
	public String cerca(Model model, @RequestParam("param") String param) {
		String[] input = {param};
		//se contiene più di una parola, ricerca ogni singola parola
		if(param.contains(" ")) {
			input = param.split(" ");
		}
		//set per evitare i duplicati
		Set<Fotografo> risultato = new TreeSet<>();
		for(String p : input) {
			risultato.addAll(fotografo.fotografoPerNome(p));
			risultato.addAll(fotografo.fotografoPerCognome(p));
		}
		
		model.addAttribute("fotografi", risultato);
		return "home";
	}

}
