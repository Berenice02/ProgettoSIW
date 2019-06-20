package it.uniroma3.siw.progetto.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.progetto.models.Album;
import it.uniroma3.siw.progetto.models.Foto;
import it.uniroma3.siw.progetto.models.Fotografo;
import it.uniroma3.siw.progetto.repositories.FotoRepository;

@Service
public class FotoServices {
	
	public static String upload = System.getProperty("user.dir")+"/src/main/resources/static/images";
	
	@Autowired
	private FotoRepository repo;

	@Transactional
	public Foto salvaFoto(MultipartFile file, Album album, Fotografo fotografo) {
		Foto f = null;
		if(!file.isEmpty()) {
			f = new Foto(file.getOriginalFilename(), album);
			//per creare le cartelle in locale
			Path cartella = Paths.get(upload, fotografo.getId().toString(), album.getId().toString());
			new File(cartella.toString()).mkdirs();
			//per avere il percorso completo
			Path percorsoFile = Paths.get(cartella.toString(), file.getOriginalFilename());
			try {
				Files.write(percorsoFile, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			repo.save(f);
		}
		return f;
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
	
	@Transactional
	public void rimuoviFoto(Long id) {
		Foto f = repo.findById(id).get();
		repo.delete(f);
		try {
			Files.deleteIfExists(Paths.get(upload, f.getFotografo().getId().toString(), f.getAlbum().getId().toString(), f.getNome()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void deleteAll() {
		repo.deleteAll();
	}
	
}
