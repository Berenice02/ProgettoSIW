package it.uniroma3.siw.progetto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.progetto.models.*;
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
}
