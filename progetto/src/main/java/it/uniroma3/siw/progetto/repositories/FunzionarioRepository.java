package it.uniroma3.siw.progetto.repositories;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.progetto.models.Funzionario;

public interface FunzionarioRepository extends CrudRepository<Funzionario, Long> {

}