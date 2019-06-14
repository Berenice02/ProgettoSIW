package it.uniroma3.siw.progetto.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.progetto.models.Album;
import it.uniroma3.siw.progetto.models.Fotografo;

public interface AlbumRepository extends CrudRepository<Long, Album>{
	public List<Album> findByNome(String nome);
	public List<Album> findByFotografo(Fotografo fotografo);
}
