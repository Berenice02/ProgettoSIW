package it.uniroma3.siw.progetto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.progetto.models.*;
import it.uniroma3.siw.progetto.repositories.FotoRepository;

@Service
public class FotoServices {
	@Autowired
	private FotoRepository repo;

	@Transactional
	public Foto salvaFoto(Foto foto) {
		return repo.save(foto);
	}
	
	@Transactional
	public List<Foto> salvaListaFoto(List<Foto> listaFoto){
		return (List<Foto>) repo.saveAll(listaFoto);
	}
	
	@Transactional
	public Foto fotoPerId(Long id){
		return repo.findById(id).get();
	}
	
	@Transactional
	public List<Foto> fotoPerNome(String nome){
		return repo.findByNome(nome);
	}

	@Transactional
	public List<Foto> fotoPerAlbum(Album album){
		return repo.findByAlbum(album);
	}
	
}
