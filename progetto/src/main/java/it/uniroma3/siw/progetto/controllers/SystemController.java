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

/*controller che gestisce la homepage*/
@Controller
public class SystemController {
	
	@Autowired
	FotografoServices fotografo;
	@Autowired
	AlbumServices album;
	@Autowired
	FotoServices foto;
	
	/*semplicemente per visualizzare la home con i primi 10 fotografi del database*/
	@RequestMapping(value = "/")
	public String root(Model model) {
		model.addAttribute("fotografi", this.fotografo.primi10Fotografi());
		return "home";
	}
	
	/* serve per restituire la pagina di login */
	@RequestMapping("/login")
	public String autentica() {
		return "login.html";
	}
	
	/*per visualizzare i risultati di una ricerca.
	 * viene chiamato cliccando "cerca" sulla home
	 */
	@GetMapping(value = "/cerca")
	public String cerca(Model model, @RequestParam("param") String param) {
		String[] input = {param};
		//se il testo della ricerca contiene più di una parola, ricerca ogni singola parola
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
		
		//li aggiungo al modello divisi tra fotografi, album e foto
		model.addAttribute("fotografi", fotografi);
		model.addAttribute("albums", albums);
		model.addAttribute("fotografie", fotografie);
		return "search";
	}

}
