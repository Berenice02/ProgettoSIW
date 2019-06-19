package it.uniroma3.siw.progetto.models;

import java.util.ArrayList;
import java.util.List;

public final class Regioni {
	
	private List<String> regioni;
	
	public Regioni() {
		this.regioni = new ArrayList<String>();
		regioni.add("Abruzzo");
		regioni.add("Basilicata");
		regioni.add("Calabria");
		regioni.add("Campania");
		regioni.add("Emilia-Romagna");
		regioni.add("Friuli-Venezia Giulia");
		regioni.add("Lazio");
		regioni.add("Liguria");
		regioni.add("Lombardia");
		regioni.add("Marche");
		regioni.add("Molise");
		regioni.add("Piemonte");
		regioni.add("Puglia");
		regioni.add("Sardegna");
		regioni.add("Sicilia");
		regioni.add("Toscana");
		regioni.add("Trentino-Alto Adige");
		regioni.add("Umbria");
		regioni.add("Valle d'Aosta");
		regioni.add("Veneto");
	}

	public List<String> getRegioni() {
		return regioni;
	}

}
