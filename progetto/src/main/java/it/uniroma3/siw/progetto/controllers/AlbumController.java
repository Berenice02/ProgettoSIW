package it.uniroma3.siw.progetto.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.progetto.models.Album;
import it.uniroma3.siw.progetto.models.Foto;
import it.uniroma3.siw.progetto.models.Fotografo;
import it.uniroma3.siw.progetto.services.AlbumServices;
import it.uniroma3.siw.progetto.services.AlbumValidator;
import it.uniroma3.siw.progetto.services.FotoServices;
import it.uniroma3.siw.progetto.services.FotografoServices;

@Controller
public class AlbumController {
	@Autowired
	AlbumServices services;

	@Autowired
	AlbumValidator validator;

	@Autowired
	FotografoServices fotografo;
	
	@Autowired
	FotoServices foto;

	/*per creare un nuovo album. viene chiamato cliccando "nuovo album" 
	 * nella pagina del fotografo (Fotografo)
	 * o in quella per modificare i dati del fotografo (FotografoForm)
	 */
	@RequestMapping(value = "/fotografo/{id}/nuovoAlbum")
	public String nuovoAlbum(@PathVariable("id") Long id, Model model) {
		model.addAttribute("album", new Album());
		model.addAttribute("fotografo", fotografo.fotografoPerId(id));
		SystemController.getUtenteAndRole(model);
		return "albumForm";
	}

	/*per salvare un nuovo album o aggiornare i dati di uno già esistente
	 * viene chiamato cliccando "salva" da AlbumForm
	 */
	@PostMapping(value = "/fotografo/{idFotografo}/salvaAlbum/{idAlbum}")
	public String salvaAlbum(@PathVariable("idFotografo") Long idFotografo,
			@Valid @ModelAttribute("album") Album album, Model model,
			@PathVariable("idAlbum") String idAlbum) {
		/*l'id dell'album viene passato come string perché il valore null che assume 
		 * nella creazione di un nuovo album è una stringa.
		 * Quando l'album esiste (e quindi ha un id) il Long si recupera
		 * tramite il metodo statico Long.decode(String s)
		 * Invece l'id del fotografo esiste sempre quindi può rimanere long
		 */
		Fotografo f = fotografo.fotografoPerId(idFotografo);
		if(!idAlbum.equals("null")) {
			Long ID = Long.decode(idAlbum);
			album.setPropic(this.services.albumPerId(ID).getPropic());
			this.services.aggiornaAlbum(ID, album);
		}
		else {
			album.setFotografo(f);
			services.salvaAlbum(album);
		}
		model.addAttribute("fotografo", f);
		model.addAttribute("albums", services.albumPerFotografo(f));
		return "fotografo";
	}

	/*per modificare i dati dell'album. viene chiamato cliccando "modifica informazioni"
	 * nella pagina di visualizzazione dell'album (Album)
	 * è incompleto perché manca la gestione delle foto contenute nell'album
	 */
	@GetMapping(value = "/fotografo/{idFotografo}/album/{idAlbum}/modifica")
	public String modificaAlbum(@PathVariable("idAlbum") Long idAlbum, Model model, @PathVariable("idFotografo") Long idFotografo) {
		Album a = this.services.albumPerId(idAlbum);
		List<Foto> fotografie = this.foto.fotoPerAlbum(a);
		
		model.addAttribute("album", a);
		model.addAttribute("fotografo", fotografo.fotografoPerId(idFotografo));
		model.addAttribute("fotografie", fotografie);
		SystemController.getUtenteAndRole(model);
		return "albumForm";
	}

	/*per visualizzare un album del fotografo. viene chiamato cliccando "visualizza"
	 * nella pagina del fotografo(Fotografo) o "vedi foto" nei risultati di una ricerca
	 * Altrimenti, senza id vengono visualizzate tutti gli album di quel fotografo
	 */
	@RequestMapping(value = "/fotografo/{idFotografo}/album/{idAlbum}")
	public String getAlbum(@PathVariable("idAlbum") Long idAlbum, Model model, @PathVariable("idFotografo") Long idFotografo, Authentication auth) {
		Fotografo f = fotografo.fotografoPerId(idFotografo);
		if(idAlbum!=null) {
			Album a = this.services.albumPerId(idAlbum);
			List<Foto> fotografie = this.foto.fotoPerAlbum(a);
			
			model.addAttribute("fotografo", f);
			if(auth != null)
				SystemController.getUtenteAndRole(model);
			model.addAttribute("album", a);
			model.addAttribute("fotografie", fotografie);
			model.addAttribute("path", f.getId().toString()+"/"+a.getId().toString());

			return "album";
		}
		else {
			model.addAttribute("fotografo", f);
			model.addAttribute("albums", services.albumPerFotografo(f));
			return "fotografo";
		}
	}
}