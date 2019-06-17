package it.uniroma3.siw.progetto.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.progetto.models.Album;
import it.uniroma3.siw.progetto.services.AlbumServices;
import it.uniroma3.siw.progetto.services.AlbumValidator;
import it.uniroma3.siw.progetto.services.FotografoServices;

@Controller
public class AlbumController {
	@Autowired
	AlbumServices services;
	
	@Autowired
	AlbumValidator validator;
	
	@Autowired
	FotografoServices fotografo;
	
	@RequestMapping(value = "/fotografo/{id}/nuovoAlbum")
	public String nuovoAlbum(@PathVariable("id") Long id, Model model) {
		model.addAttribute("album", new Album());
		model.addAttribute(fotografo.fotografoPerId(id));
		return "albumForm";
	}
	
	@PostMapping(value = "/fotografo/{id}/salvaAlbum")
	public String salvaAlbum(@PathVariable("id") Long id, @Valid @ModelAttribute("album") Album album, Model model, BindingResult br) {
		this.validator.validate(album, br);
		if(!br.hasErrors()) {
			album.setFotografo(fotografo.fotografoPerId(id));
			services.salvaAlbum(album);
			model.addAttribute("fotografi", fotografo.primi10Fotografi());
			return "home";
		}
		else
			return "albumForm";
	}
	
	@RequestMapping(value = "/album/{id}")
	public String getAlbum(@PathVariable("id") Long id, Model model) {
		if(id!=null) {
			model.addAttribute("album", this.services.albumPerId(id));
			return "album";
		}
		return "home";
	}

}
