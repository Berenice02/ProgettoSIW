package it.uniroma3.siw.progetto.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Fotografo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String cognome;
	private String mail;
	private String numero;
	
	@OneToMany(mappedBy = "fotografo")
	private List<Album> album;
	
	public Fotografo() {
		//no op
	}
	
	public Fotografo(String nome, String cognome, String mail, String numero) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.mail = mail;
		this.numero = numero;
		this.album = new ArrayList<>();
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public List<Album> getAlbum() {
		return album;
	}
	public void setAlbum(List<Album> album) {
		this.album = album;
	}
}
