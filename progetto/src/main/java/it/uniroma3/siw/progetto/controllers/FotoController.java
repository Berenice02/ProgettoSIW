package it.uniroma3.siw.progetto.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	 */
	@PostMapping(value = "/fotografo/{idFotografo}/album/{idAlbum}/salvaFoto")
	public String nuovoAlbum(@PathVariable("idFotografo") Long idFotografo,
			@PathVariable("idAlbum") Long idAlbum, Model model,
			@RequestParam(value = "files") MultipartFile[] files) {
		//prendi i riferimenti ad album e foto
		Fotografo f = fotografo.fotografoPerId(idFotografo);
		Album a = album.albumPerId(idAlbum);
		//salva ogni foto
		List<Foto> fotografie = new ArrayList<>();
		for(MultipartFile file : files) {
			Foto foto = this.services.salvaFoto(file, a, f);
			fotografie.add(foto);
		}
		//aggiungi tutto al model
		model.addAttribute("fotografie", fotografie);
		model.addAttribute("fotografo", f);
		model.addAttribute("album", a);
		//SystemController.getUtenteAndRole(model);
		return "album";
	}

}
