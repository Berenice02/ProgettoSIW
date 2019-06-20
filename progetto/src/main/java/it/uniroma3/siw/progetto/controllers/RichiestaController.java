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
import it.uniroma3.siw.progetto.models.Foto;
import it.uniroma3.siw.progetto.models.Fotografo;
import it.uniroma3.siw.progetto.models.Paesi;
import it.uniroma3.siw.progetto.models.Regioni;
import it.uniroma3.siw.progetto.models.Richiesta;
import it.uniroma3.siw.progetto.services.AlbumServices;
import it.uniroma3.siw.progetto.services.FotoServices;
import it.uniroma3.siw.progetto.services.FotografoServices;
import it.uniroma3.siw.progetto.services.RichiestaServices;
import it.uniroma3.siw.progetto.services.RichiestaValidator;

@Controller
public class RichiestaController {
	@Autowired
	RichiestaServices services;

	@Autowired
	RichiestaValidator validator;
	
	@Autowired
	FotografoServices fotografo;
	
	@Autowired
	FotoServices foto;
	
	@Autowired
	AlbumServices album;
	
	public Richiesta richiesta = new Richiesta();

	/*per creare una nuova richiesta. viene chiamato cliccando "carrello" nella toolbar
	 * c'è da modificare per gestire le foto
	 */
	@RequestMapping(value = "/nuovaRichiesta")
	public String nuovaRichiesta(Model model) {
		int numFoto = richiesta.getFoto().size();
		model.addAttribute("numFoto", numFoto);
		model.addAttribute("richiesta", richiesta);
		model.addAttribute("fotografie", richiesta.getFoto().values());
		model.addAttribute("paesi", (new Paesi()).getPaesi());
		model.addAttribute("regioni", (new Regioni()).getRegioni());
		return "richiestaForm";
	}

	/*per salvare una nuova richiesta. viene chiamato cliccando "salva" da RichiestaForm
	 */
	@PostMapping(value = "/salvaRichiesta")
	public String salvaRichiesta(@Valid @ModelAttribute Richiesta richiesta, Model model) {
		Richiesta r = services.salvaRichiesta(richiesta);
		model.addAttribute("richiesta",this.services.richiestaPerId(r.getId()));
		model.addAttribute("fotografie", this.richiesta.getFoto().values());
		richiesta = new Richiesta();
		return "richiesta";
	}

	/* per visualizzare le prime 10 richieste dal database
	 * viene chiamato cliccando "richieste" nella toolbar
	 */
	@GetMapping(value = "/richieste")
	public String getRichieste(Model model) {
		model.addAttribute("richieste", this.services.prime10Richieste());
		SystemController.getUtenteAndRole(model);
		return "richieste";
	}

	/*per visualizzare una richiesta. viene chiamato cliccando sul nome e cognome
	 * del cliente che ha effettuato la richiesta
	 * Altrimenti, senza id vengono visualizzate le prime 10 richieste dal database
	 */
	@GetMapping(value = "/richiesta/{id}")
	public String getRichiesta(@PathVariable("id") Long id, Model model) {
		SystemController.getUtenteAndRole(model);
		if(id!=null) {
			model.addAttribute("richiesta", this.services.richiestaPerId(id));
			model.addAttribute("fotografie", this.richiesta.getFoto().keySet());
			return "richiesta";
		}
		else {
			model.addAttribute("richieste", this.services.prime10Richieste());
			return "richieste";
		}
	}
	
	/*per aggiungere una foto nel carrello */
	@GetMapping(value = "/fotografo/{idFotografo}/album/{idAlbum}/foto/{idFoto}/aggiungiFoto")
	public String aggiungiFoto(@PathVariable("idAlbum") Long idAlbum, Model model,
			@PathVariable("idFotografo") Long idFotografo,
			@PathVariable("idFoto") Long idFoto) {
		//prendi i riferimenti ad album e fotografo
		Fotografo f = fotografo.fotografoPerId(idFotografo);
		Album a = album.albumPerId(idAlbum);
		Foto foto = this.foto.fotoPerId(idFoto);
		this.richiesta.getFoto().put(idFoto, foto);
		model.addAttribute("fotografo", f);
		model.addAttribute("album", a);
		model.addAttribute("foto", foto);
		model.addAttribute("fotografie", this.richiesta.getFoto().values());
		model.addAttribute("richiesta", richiesta);
		model.addAttribute("paesi", (new Paesi()).getPaesi());
		model.addAttribute("regioni", (new Regioni()).getRegioni());
		int numFoto = richiesta.getFoto().size();
		model.addAttribute("numFoto", numFoto);
		return "richiestaForm";
	}
}