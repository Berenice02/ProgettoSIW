package it.uniroma3.siw.progetto.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.progetto.models.Funzionario;

@Repository
public interface FunzionarioRepository extends CrudRepository<Funzionario, Long> {

}