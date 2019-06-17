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
import it.uniroma3.siw.progetto.services.AlbumServices;
import it.uniroma3.siw.progetto.services.FotografoServices;
import it.uniroma3.siw.progetto.services.FotografoValidator;

@Controller
public class FotografoController {
	@Autowired
	FotografoServices services;
	
	@Autowired
	FotografoValidator validator;
	
	@Autowired
	AlbumServices album;
	
	@RequestMapping(value = "/nuovoFotografo")
	public String nuovoFotografo(Model model) {
		model.addAttribute("fotografo", new Fotografo());
		return "fotografoForm";
	}
	
	@PostMapping(value = "salvaFotografo/{id}")
	public String salvaFotografo(@Valid @ModelAttribute("fotografo") Fotografo fotografo, @PathVariable("id") String id, Model model, BindingResult br) {
		this.validator.validate(fotografo, br);
		if(!br.hasErrors()) {
			if(!id.equals("null")) {
				/*questa è una schifezza ma non trovo update nella repo*/
				Fotografo f = this.services.fotografoPerId(Long.decode(id));
				fotografo.setAlbum(f.getAlbum());
				this.services.rimuoviFotografo(f);
			}
			services.salvaFotografo(fotografo);
			model.addAttribute("fotografi", services.primi10Fotografi());
			return "home";
		}
		else
			return "fotografoForm";
	}
	
	@GetMapping(value = "/fotografo/{id}/modifica")
	public String modificaFotografo(@PathVariable("id") Long id, Model model) {
		Fotografo f = this.services.fotografoPerId(id);
		model.addAttribute("fotografo", f);
		model.addAttribute("albums", album.albumPerFotografo(f));
		return "fotografoForm";
	}
	
	@GetMapping(value = "/fotografi")
	public String getFotografi(Model model) {
		model.addAttribute("fotografi", this.services.primi10Fotografi());
		return "fotografi";
	}
	
	@RequestMapping(value = "/fotografo/{id}")
	public String getFotografo(@PathVariable("id") Long id, Model model) {
		if(id!=null) {
			Fotografo f = this.services.fotografoPerId(id);
			model.addAttribute("fotografo", f);
			model.addAttribute("albums", this.album.albumPerFotografo(f));
			return "fotografo";
		}
		else {
			model.addAttribute("fotografi", this.services.primi10Fotografi());
			return "fotografi";
		}
	}

}
