package it.uniroma3.siw.progetto.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.progetto.models.Album;
import it.uniroma3.siw.progetto.models.Foto;
import it.uniroma3.siw.progetto.models.Fotografo;

@Repository
public interface FotoRepository extends CrudRepository<Foto, Long>{
	public List<Foto> findByNome(String nome);
	public List<Foto> findByFotografo(Fotografo fotografo);
	public List<Foto> findByAlbum(Album album);

}
