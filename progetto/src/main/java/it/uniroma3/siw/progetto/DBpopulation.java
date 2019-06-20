package it.uniroma3.siw.progetto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.progetto.models.Album;
import it.uniroma3.siw.progetto.models.Foto;
import it.uniroma3.siw.progetto.models.Fotografo;
import it.uniroma3.siw.progetto.models.Funzionario;
import it.uniroma3.siw.progetto.models.Richiesta;
import it.uniroma3.siw.progetto.repositories.FunzionarioRepository;
import it.uniroma3.siw.progetto.repositories.RichiestaRepository;
import it.uniroma3.siw.progetto.services.AlbumServices;
import it.uniroma3.siw.progetto.services.FotoServices;
import it.uniroma3.siw.progetto.services.FotografoServices;

/*classe per popolare il database con fotografi, album e richieste
 * mancano le foto e gli admin
 */
@Component
public class DBpopulation implements ApplicationRunner {
	@Autowired
	private RichiestaRepository richiesta;
	
	@Autowired
	private FotografoServices fotografo;
	
	@Autowired
	private AlbumServices album;
	
	@Autowired
	private FotoServices foto;
	
	@Autowired
	private FunzionarioRepository funzionario;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.deleteAll();
		this.addAll();
	}
	
	private void deleteAll() {
		richiesta.deleteAll();
		fotografo.deleteAll();
		album.deleteAll();
		foto.deleteAll();
		funzionario.deleteAll();
	}
	
	private void addAll() {
		Fotografo f1 = new Fotografo("alessandra", "bianchi", "a.b@gmail.com", "3491234567");
		Fotografo f2 = new Fotografo("carlo", "verdi", "c.v@gmail.com", "3411234567");
		Fotografo f3 = new Fotografo("paola", "neri", "p.n@gmail.com", "3431234567");
		Fotografo f4 = new Fotografo("pietro", "russo", "p.r@gmail.com", "3441234567");
		fotografo.salvaFotografo(f1);
		fotografo.salvaFotografo(f2);
		fotografo.salvaFotografo(f3);
		fotografo.salvaFotografo(f4);
		
		Album a1 = new Album("albe", f1);
		Album a2 = new Album("tramonti", f1);
		Album a3 = new Album("gatti", f2);
		Album a4 = new Album("cani", f2);
		Album a5 = new Album("uccelli", f2);
		Album a6 = new Album("rettili", f2);
		Album a7 = new Album("lampioni", f3);
		Album a8 = new Album("cibo", f4);
		
		List<Album> albums = new ArrayList<Album>() {
			private static final long serialVersionUID = 1L;
		{
			add(a1);
			add(a2);
			add(a3);
			add(a4);
			add(a5);
			add(a6);
			add(a7);
			add(a8);
		}};
		
		album.saveAll(albums);
		
		String upload = System.getProperty("user.dir")+"/src/main/resources/static/images";	
		
		for(Album a : albums) {
			Fotografo f = a.getFotografo();
			Path pathCartella = Paths.get(upload, f.getId().toString(), a.getId().toString());
			File cartella = pathCartella.toFile();
			for(File file : cartella.listFiles()) {
				if(file.isFile()) {
					String name = file.getName();
					byte[] content = null;
					Path path = Paths.get(cartella.toString(), file.getName());
					try {
						content = Files.readAllBytes(path);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					MultipartFile result = new Foto(name, a, content);
					
					Foto ultima = foto.salvaFoto(result, a, f);
					f.setPropic(ultima);
					fotografo.aggiornaFotografo(f.getId(), f);
					
					a.setPropic(ultima);
					album.aggiornaAlbum(a.getId(), a);
					}
			}
		}
		
		Richiesta r1 = new Richiesta("matteo", "giunta", "m.g@gmail.com", "3401234567",
				"mttgnt97t12h501s", "via vasca navale", "79", "Italia", "Lazio", "00131");
		Richiesta r2 = new Richiesta("elisa", "foderaro", "e.f@gmail.com", "3421234567",
				"fdrlse98b41h501q", "viale marconi", "596", "Italia", "Lazio", "00132");
		Richiesta r3 = new Richiesta("mario", "rossi", "m.r@gmail.com", "3471234567",
				"mrarss70t20h501n", "via ostiense", "159", "Italia", "Lazio", "00154");
		richiesta.save(r1);
		richiesta.save(r2);
		richiesta.save(r3);
		
		Funzionario fu1 = new Funzionario("Matteo", "Giunta", "MTGN97", null);
		String fu1pass = new BCryptPasswordEncoder().encode("mgpass");
		fu1.setPassword(fu1pass);
		funzionario.save(fu1);
		
		Funzionario fu2 = new Funzionario("Elisa", "Foderaro", "ELFD98", null);
		String fu2pass = new BCryptPasswordEncoder().encode("efpass");
		fu2.setPassword(fu2pass);
		funzionario.save(fu2);
	}
}