package it.uniroma3.siw.progetto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.progetto.models.Album;
import it.uniroma3.siw.progetto.models.Foto;
import it.uniroma3.siw.progetto.models.Fotografo;
import it.uniroma3.siw.progetto.services.AlbumServices;
import it.uniroma3.siw.progetto.services.FotoServices;
import it.uniroma3.siw.progetto.services.FotografoServices;

@Controller
public class FotoController {
	@Autowired
	FotografoServices fotografo;
	
	@Autowired
	AlbumServices album;
	
	@Autowired
	FotoServices services;
	
	/*per salvare una foto. viene chiamato cliccando "carica" 
	 * nella pagina dell'album (Album)
	 * o in quella per modificare i dati dell'album (AlbumForm)
	 * NON CARICA LA VISUALIZZAZIONE DELLE FOTO PER UN BUG DI ECLIPSE
	 * BISOGNA FARE REFRESH AL PROGETTO
	 */
	@PostMapping(value = "/fotografo/{idFotografo}/album/{idAlbum}/salvaFoto")
	public String nuovaFoto(@PathVariable("idFotografo") Long idFotografo,
			@PathVariable("idAlbum") Long idAlbum, Model model,
			@RequestParam(value = "files") MultipartFile[] files) {
		//prendi i riferimenti ad album e fotografo
		Fotografo f = fotografo.fotografoPerId(idFotografo);
		Album a = album.albumPerId(idAlbum);
		//salva ogni foto
		List<Foto> fotografie = this.services.fotoPerAlbum(a);
		for(MultipartFile file : files) {
			Foto foto = this.services.salvaFoto(file, a, f);
			fotografie.add(foto);
		}

		//imposta l'ultima foto aggiunta come propic del fotografo
		f.setPropic(fotografie.get((fotografie.size())-1));
		fotografo.aggiornaFotografo(idFotografo, f);
		
		//aggiungi tutto al model
		model.addAttribute("fotografo", f);
		model.addAttribute("album", a);
		model.addAttribute("path", f.getId().toString() +"/" + a.getId().toString());
		model.addAttribute("fotografie", fotografie);
		//SystemController.getUtenteAndRole(model);
		return "album";
	}
	
	/*per visualizzare una foto della foto. viene chiamato cliccando "visualizza"
	 * nell'album o "vedi" nei risultati di una ricerca
	 * Altrimenti, senza id vengono visualizzate tutte le foto di quell'album
	 */
	@GetMapping(value = "/fotografo/{idFotografo}/album/{idAlbum}/foto/{idFoto}")
	public String getFoto(@PathVariable("idAlbum") Long idAlbum, Model model,
			@PathVariable("idFotografo") Long idFotografo, @PathVariable("idFoto") Long idFoto,
			Authentication auth) {
		//prendi i riferimenti ad album e fotografo
		Fotografo f = fotografo.fotografoPerId(idFotografo);
		Album a = album.albumPerId(idAlbum);
		if(auth != null)
			SystemController.getUtenteAndRole(model);
		if(idFoto!=null) {
			Foto foto = this.services.fotoPerId(idFoto);
			model.addAttribute("fotografo", f);
			model.addAttribute("album", a);
			model.addAttribute("path", f.getId().toString() + '/' + a.getId().toString());
			model.addAttribute("foto", foto);
			return "foto";
		}
		else {
			model.addAttribute("fotografo", f);
			model.addAttribute("albums", album.albumPerFotografo(f));
			return "album";
		}
	}

}
