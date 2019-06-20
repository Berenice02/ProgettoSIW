package it.uniroma3.siw.progetto.controllers;

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

	/*per creare un nuovo fotografo. viene chiamato cliccando "aggiungi un nuovo fotografo" 
	 * nella pagina dei fotografi (Fotografi)
	 */
	@RequestMapping(value = "/nuovoFotografo")
	public String nuovoFotografo(Model model) {
		model.addAttribute("fotografo", new Fotografo());
		SystemController.getUtenteAndRole(model);
		return "fotografoForm";
	}

	/*per salvare un nuovo fotografo o aggiornare i dati di uno già esistente
	 * viene chiamato cliccando "salva" da FotografoForm
	 */
	@PostMapping(value = "/salvaFotografo/{idFotografo}")
	public String salvaFotografo(@Valid @ModelAttribute("fotografo") Fotografo fotografo,
			@PathVariable(value = "idFotografo") String idFotografo, Model model) {
		/*l'id viene passato come string perché il valore null che assume 
		 * nella creazione di un nuovo fotografo è una stringa.
		 * Quando il fotografo esiste (e quindi ha un id) il Long si recupera
		 * tramite il metodo statico Long.decode(String s)
		 */
		Fotografo f;
		if(!idFotografo.equals("null")) {
			Long ID = Long.decode(idFotografo);
			fotografo.setPropic(this.services.fotografoPerId(ID).getPropic());
			f = services.aggiornaFotografo(ID, fotografo);
		}
		else
			f = services.salvaFotografo(fotografo);
		model.addAttribute("fotografo", f);
		model.addAttribute("albums", album.albumPerFotografo(f));
		SystemController.getUtenteAndRole(model);
		return "fotografo";
	}

	/*per modificare i dati del fotografo. viene chiamato cliccando "modifica informazioni"
	 * nella pagina di visualizzazione del fotografo (Fotografo)
	 */
	@GetMapping(value = "/fotografo/{id}/modifica")
	public String modificaFotografo(@PathVariable("id") Long id, Model model) {
		Fotografo f = this.services.fotografoPerId(id);
		model.addAttribute("fotografo", f);
		model.addAttribute("albums", album.albumPerFotografo(f));
		SystemController.getUtenteAndRole(model);
		return "fotografoForm";
	}

	/* per visualizzare i fotografi del database
	 * viene chiamato cliccando "fotografi" nella toolbar
	 */
	@GetMapping(value = "/fotografi")
	public String getFotografi(Model model, Authentication auth) {
		model.addAttribute("fotografi", this.services.tuttiFotografi());
		if(auth != null)
			SystemController.getUtenteAndRole(model);
		return "fotografi";
	}

	/*per visualizzare un fotografo. viene chiamato cliccando "vedi dettagli"
	 * nella pagina di inizio (Home), in quella dei fotografi (Fotografi) 
	 * e nei risultati di una ricerca
	 * Altrimenti, senza id vengono visualizzati i primi 10 fotografi del database
	 */
	@RequestMapping(value = "/fotografo/{id}")
	public String getFotografo(@PathVariable("id") Long id, Model model, Authentication auth) {
		if(id!=null) {
			Fotografo f = this.services.fotografoPerId(id);
			model.addAttribute("fotografo", f);
			model.addAttribute("albums", this.album.albumPerFotografo(f));
			if(auth != null)
				SystemController.getUtenteAndRole(model);
			return "fotografo";
		}
		else {
			model.addAttribute("fotografi", this.services.primi10Fotografi());
			return "fotografi";
		}
	}
}