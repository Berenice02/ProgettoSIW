package it.uniroma3.siw.progetto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.progetto.models.Album;
import it.uniroma3.siw.progetto.models.Fotografo;
import it.uniroma3.siw.progetto.models.Richiesta;
import it.uniroma3.siw.progetto.repositories.AlbumRepository;
import it.uniroma3.siw.progetto.repositories.FotoRepository;
import it.uniroma3.siw.progetto.repositories.FotografoRepository;
import it.uniroma3.siw.progetto.repositories.RichiestaRepository;

@Component
public class DBpopulation implements ApplicationRunner {
	@Autowired
	private RichiestaRepository richiesta;
	
	@Autowired
	private FotografoRepository fotografo;
	
	@Autowired
	private AlbumRepository album;
	
	@Autowired
	private FotoRepository foto;

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
	}
	
	private void addAll() {
		Fotografo f1 = new Fotografo("alessandra", "bianchi", "a.b@gmail.com", "3491234567");
		Fotografo f2 = new Fotografo("carlo", "verdi", "c.v@gmail.com", "3411234567");
		Fotografo f3 = new Fotografo("paola", "neri", "p.n@gmail.com", "3431234567");
		Fotografo f4 = new Fotografo("pietro", "russo", "p.r@gmail.com", "3441234567");
		fotografo.save(f1);
		fotografo.save(f2);
		fotografo.save(f3);
		fotografo.save(f4);
		
		Album a1 = new Album("albe", f1);
		Album a2 = new Album("tramonti", f1);
		Album a3 = new Album("gatti", f2);
		Album a4 = new Album("cani", f2);
		Album a5 = new Album("uccelli", f2);
		Album a6 = new Album("rettili", f2);
		Album a7 = new Album("strada", f3);
		Album a8 = new Album("lampioni", f3);
		Album a9 = new Album("ristoranti", f3);
		Album a10 = new Album("cibo", f4);
		album.save(a1);
		album.save(a2);
		album.save(a3);
		album.save(a4);
		album.save(a5);
		album.save(a6);
		album.save(a7);
		album.save(a8);
		album.save(a9);
		album.save(a10);
		
		Richiesta r1 = new Richiesta("matteo", "giunta", "m.g@gmail.com", "3401234567",
				"mttgnt97t12h501s", "via vasca navale", "79", "Italia", "Lazio", "00131");
		Richiesta r2 = new Richiesta("elisa", "foderaro", "e.f@gmail.com", "3421234567",
				"fdrlse98b41h501q", "viale marconi", "596", "Italia", "Lazio", "00132");
		Richiesta r3 = new Richiesta("mario", "rossi", "m.r@gmail.com", "3471234567",
				"mrarss70t20h501n", "via ostiense", "159", "Italia", "Lazio", "00154");
		richiesta.save(r1);
		richiesta.save(r2);
		richiesta.save(r3);
	}

}
