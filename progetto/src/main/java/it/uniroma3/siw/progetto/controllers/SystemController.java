package it.uniroma3.siw.progetto.controllers;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.progetto.models.Album;
import it.uniroma3.siw.progetto.models.Foto;
import it.uniroma3.siw.progetto.models.Fotografo;
import it.uniroma3.siw.progetto.services.AlbumServices;
import it.uniroma3.siw.progetto.services.FotoServices;
import it.uniroma3.siw.progetto.services.FotografoServices;

@Controller
public class SystemController {
	
	@Autowired
	FotografoServices fotografo;
	@Autowired
	AlbumServices album;
	@Autowired
	FotoServices foto;
	
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
		Set<Fotografo> fotografi = new TreeSet<>();
		for(String p : input) {
			fotografi.addAll(fotografo.fotografoPerNome(p));
			fotografi.addAll(fotografo.fotografoPerCognome(p));
		}
		Set<Album> albums = new TreeSet<>();
		for(String p : input) {
			albums.addAll(album.albumPerNome(p));
		}
		Set<Foto> fotografie = new TreeSet<>();
		for(String p:input) {
			fotografie.addAll(foto.fotoPerNome(p));
		}
		
		model.addAttribute("fotografi", fotografi);
		model.addAttribute("albums", albums);
		model.addAttribute("fotografie", fotografie);
		return "search";
	}

}
