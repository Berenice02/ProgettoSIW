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

import it.uniroma3.siw.progetto.models.Paesi;
import it.uniroma3.siw.progetto.models.Regioni;
import it.uniroma3.siw.progetto.models.Richiesta;
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

	/*per creare una nuova richiesta. viene chiamato cliccando "carrello" nella toolbar
	 * c'è da modificare per gestire le foto
	 */
	@RequestMapping(value = "/nuovaRichiesta")
	public String nuovaRichiesta(Model model) {
		model.addAttribute("richiesta", new Richiesta());
		model.addAttribute("paesi", (new Paesi()).getPaesi());
		model.addAttribute("regioni", (new Regioni()).getRegioni());
		return "richiestaForm";
	}

	/*per salvare una nuova richiesta. viene chiamato cliccando "salva" da RichiestaForm
	 */
	@PostMapping(value = "/salvaRichiesta")
	public String salvaRichiesta(@Valid @ModelAttribute("richiesta") Richiesta richiesta, Model model) {
		services.salvaRichiesta(richiesta);
		model.addAttribute("fotografi", fotografo.primi10Fotografi());
		return "home";
	}

	/* per visualizzare le prime 10 richieste dal database
	 * viene chiamato cliccando "richieste" nella toolbar
	 */
	@GetMapping(value = "/richieste")
	public String getRichieste(Model model) {
		model.addAttribute("richieste", this.services.prime10Richieste());
		return "richieste";
	}

	/*per visualizzare una richiesta. viene chiamato cliccando sul nome e cognome
	 * del cliente che ha effettuato la richiesta
	 * Altrimenti, senza id vengono visualizzate le prime 10 richieste dal database
	 */
	@GetMapping(value = "/richiesta/{id}")
	public String getRichiesta(@PathVariable("id") Long id, Model model) {
		if(id!=null) {
			model.addAttribute("richiesta", this.services.richiestaPerId(id));
			return "richiesta";
		}
		else {
			model.addAttribute("richieste", this.services.prime10Richieste());
			return "richieste";
		}
	}

}
