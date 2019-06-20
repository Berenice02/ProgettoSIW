package it.uniroma3.siw.progetto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.progetto.models.Album;
import it.uniroma3.siw.progetto.models.Fotografo;
import it.uniroma3.siw.progetto.repositories.AlbumRepository;

@Service
public class AlbumServices {
	@Autowired
	private AlbumRepository repo;

	@Transactional
	public Album salvaAlbum(Album album) {
		return repo.save(album);
	}
	
	@Transactional
	public Iterable<Album> saveAll(Iterable<Album> entities) {
		return repo.saveAll(entities);
	}
	
	@Transactional
	public Album aggiornaAlbum(Long id, Album a) {
		Album album = repo.findById(id).get();
		album.setNome(a.getNome());
		album.setFoto(a.getFoto());
		album.setPropic(a.getPropic());
		//solo questi tre perché fotografo e data non si possono modificare
		return album;
	}
	
	@Transactional
	public Album albumPerId(Long id){
		return repo.findById(id).get();
	}
	
	@Transactional
	public List<Album> albumPerNome(String nome){
		return repo.findByNome(nome);
	}

	@Transactional
	public List<Album> albumPerFotografo(Fotografo fotografo){
		return repo.findByFotografo(fotografo);
	}
	
	@Transactional
	public void rimuoviAlbum(Album album) {
		repo.delete(album);
	}
	
	@Transactional
	public void deleteAll() {
		repo.deleteAll();
	}
}
