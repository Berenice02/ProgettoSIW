package it.uniroma3.siw.progetto.controllers;

import java.util.ArrayList;
import java.util.List;

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
	// "String ... param" è per indicare che potrebbe essere uno o più param
	public String cerca(Model model, @RequestParam("param") String ... param) {
		List<Fotografo> risultato = new ArrayList<>();
		for(String p : param) {
			risultato.addAll(fotografo.fotografoPerNome(p));
			risultato.addAll(fotografo.fotografoPerCognome(p));
		}
		model.addAttribute("fotografi", risultato);
		return "fotografi";
	}

}
