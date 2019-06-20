package it.uniroma3.siw.progetto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.progetto.models.Richiesta;
import it.uniroma3.siw.progetto.repositories.RichiestaRepository;

@Service
public class RichiestaServices {
	@Autowired
	RichiestaRepository repo;
	
	@Transactional
	public Richiesta salvaRichiesta(Richiesta richiesta) {
		return repo.save(richiesta);
	}
	@Transactional
	public Richiesta richiestaPerId(Long id){
		return repo.findById(id).get();
	}
	@Transactional
	public List<Richiesta> prime10Richieste(){
		List<Richiesta> richiesta = (List<Richiesta>) repo.findAll();
		return richiesta.subList(0, Integer.min(10, richiesta.size()));
	}
	
	@Transactional
	public void deleteAll() {
		repo.deleteAll();
	}

}
