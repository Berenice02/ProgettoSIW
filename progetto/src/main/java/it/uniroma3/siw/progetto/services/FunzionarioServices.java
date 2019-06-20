package it.uniroma3.siw.progetto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.progetto.models.Funzionario;
import it.uniroma3.siw.progetto.repositories.FunzionarioRepository;

@Service
public class FunzionarioServices {
	@Autowired
	private FunzionarioRepository funzionarioRepository;
	
	@Transactional
	public Funzionario salvaFunzionario(Funzionario funzionario) {
		return this.funzionarioRepository.save(funzionario);
	}
}