package it.uniroma3.siw.progetto.models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Richiesta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/*invece di creare un altro model di cliente metto tutti i dati qui
	 * dato che non è importante ricordare i clienti
	 */
	private String nome;
	private String cognome;
	private String email;
	private String telefono;
	private String codiceFiscale;
	private String indirizzo;
	private String civico;
	private String paese;
	private String regione;
	private String cap;
	private String note;
	
	@ManyToMany
	private Map<Long, Foto> foto;
	
	public Richiesta() {
		//no op
	}

	public Richiesta(String nome, String cognome, String email, String telefono, String codiceFiscale, String indirizzo,
			String civico, String paese, String regione, String cap) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.telefono = telefono;
		this.codiceFiscale = codiceFiscale;
		this.indirizzo = indirizzo;
		this.civico = civico;
		this.paese = paese;
		this.regione = regione;
		this.cap = cap;
		this.foto = new HashMap<>();
	}

	public Richiesta(String nome, String cognome, String email, String telefono, String codiceFiscale, String indirizzo,
			String civico, String paese, String regione, String cap, String note) {
		this(nome, cognome, email, telefono, codiceFiscale, indirizzo, civico, paese, regione, cap);
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getPaese() {
		return paese;
	}

	public void setPaese(String paese) {
		this.paese = paese;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Map<Long, Foto> getFoto() {
		return foto;
	}

	public void setFoto(Map<Long, Foto> foto) {
		this.foto = foto;
	}

}
