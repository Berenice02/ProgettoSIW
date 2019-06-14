package it.uniroma3.siw.progetto.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.progetto.models.Fotografo;

@Repository
public interface FotografoRepository extends CrudRepository<Fotografo, Long>{
	public List<Fotografo> findByNome(String nome);
	public List<Fotografo> findByCognome(String cognome);
	public List<Fotografo> findByNomeAndCognome(String nome, String cognome);
}
