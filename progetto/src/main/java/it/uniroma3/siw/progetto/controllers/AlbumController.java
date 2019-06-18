package it.uniroma3.siw.progetto.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.progetto.models.Album;
import it.uniroma3.siw.progetto.models.Fotografo;
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
		model.addAttribute("fotografo", fotografo.fotografoPerId(id));
		return "albumForm";
	}

	@PostMapping(value = "/fotografo/{idFotografo}/salvaAlbum/{idAlbum}")
	public String salvaAlbum(@PathVariable("idFotografo") Long idFotografo,
			@Valid @ModelAttribute("album") Album album, Model model,
			@PathVariable("idAlbum") String idAlbum) {
		Fotografo f = fotografo.fotografoPerId(idFotografo);
		if(!idAlbum.equals("null")) {
			this.services.aggiornaAlbum(Long.decode(idAlbum), album);
		}
		else {
			album.setFotografo(f);
			services.salvaAlbum(album);
		}
		model.addAttribute("fotografo", f);
		model.addAttribute("albums", services.albumPerFotografo(f));
		return "fotografo";
	}

	@GetMapping(value = "/fotografo/{idFotografo}/album/{idAlbum}/modifica")
	public String modificaAlbum(@PathVariable("idAlbum") Long idAlbum, Model model, @PathVariable("idFotografo") Long idFotografo) {
		Album a = this.services.albumPerId(idAlbum);
		model.addAttribute("album", a);
		//da aggiungere al model le foto per album
		model.addAttribute("fotografo", fotografo.fotografoPerId(idFotografo));
		return "albumForm";
	}

	@RequestMapping(value = "/fotografo/{idFotografo}/album/{idAlbum}")
	public String getAlbum(@PathVariable("idAlbum") Long idAlbum, Model model, @PathVariable("idFotografo") Long idFotografo) {
		if(idAlbum!=null) {
			model.addAttribute("album", this.services.albumPerId(idAlbum));
			return "album";
		}
		else {
			Fotografo f = fotografo.fotografoPerId(idFotografo);
			model.addAttribute("fotografo", f);
			model.addAttribute("albums", services.albumPerFotografo(f));
			return "fotografo";
		}
	}

}
