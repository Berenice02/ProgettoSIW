package it.uniroma3.siw.progetto.controllers;

import java.util.Set;

import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
	
	@Autowired
	public static void getUtenteAndRole(Model model) {
		UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String role = details.getAuthorities().iterator().next().getAuthority();
		model.addAttribute("username", details.getUsername());
		model.addAttribute("role", role);
	}
	
	/*semplicemente per visualizzare la home con i primi 10 fotografi del database*/
	@RequestMapping(value = "/")
	public String root(Model model, Authentication auth) {
		model.addAttribute("fotografi", this.fotografo.primi10Fotografi());
		if(auth != null)
			SystemController.getUtenteAndRole(model);
		return "home";
	}
	
	/*semplicemente per visualizzare la home con i primi 10 fotografi del database*/
	@RequestMapping(value = "/welcome")
	public String welcome(Model model) {
		model.addAttribute("fotografi", this.fotografo.primi10Fotografi());
		SystemController.getUtenteAndRole(model);
		return "home";
	}
	
	/*serve per effettuare il logout*/
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated())
			new SecurityContextLogoutHandler().logout(request, response, auth);
		model.addAttribute("fotografi", this.fotografo.primi10Fotografi());
		return "home";
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