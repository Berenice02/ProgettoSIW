package it.uniroma3.siw.progetto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.progetto.models.Fotografo;
import it.uniroma3.siw.progetto.repositories.FotografoRepository;

@Service
public class FotografoServices {
	@Autowired
	private FotografoRepository repo;

	@Transactional
	public Fotografo salvaFotografo(Fotografo fotografo) {
		return repo.save(fotografo);
	}
	
	@Transactional
	public void aggiornaFotografo(Long id, Fotografo f) {
		Fotografo fotografo = repo.findById(id).get();
		fotografo.setNome(f.getNome());
		fotografo.setCognome(f.getCognome());
		fotografo.setEmail(f.getEmail());
		fotografo.setTelefono(f.getTelefono());
		fotografo.setAlbum(f.getAlbum());
		fotografo.setPropic(f.getPropic());
	}
	
	@Transactional
	public Fotografo fotografoPerId(Long id){
		return repo.findById(id).get();
	}
	
	@Transactional
	public List<Fotografo> fotografoPerNome(String nome){
		return repo.findByNome(nome);
	}
	
	@Transactional
	public List<Fotografo> fotografoPerCognome(String cognome){
		return repo.findByCognome(cognome);
	}
	
	@Transactional
	public void rimuoviFotografo(Fotografo f){
		repo.delete(f);
	}
	
	@Transactional
	public void deleteAll() {
		repo.deleteAll();
	}
	
	@Transactional
	public List<Fotografo> primi10Fotografi(){
		List<Fotografo> fotografi = (List<Fotografo>) repo.findAll();
		return fotografi.subList(0, Integer.min(10, fotografi.size()));
	}
	
	@Transactional
	public List<Fotografo> tuttiFotografi(){
		return (List<Fotografo>) repo.findAll();
	}
}
