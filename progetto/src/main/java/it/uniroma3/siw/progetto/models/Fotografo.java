package it.uniroma3.siw.progetto.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Fotografo implements Comparable<Fotografo>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String cognome;
	private String email;
	private String telefono;

	@OneToMany(mappedBy = "fotografo")
	private List<Album> album;

	public Fotografo() {
		//no op
	}

	public Fotografo(String nome, String cognome, String mail, String numero) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = mail;
		this.telefono = numero;
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
	public List<Album> getAlbum() {
		return album;
	}
	public void setAlbum(List<Album> album) {
		this.album = album;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fotografo other = (Fotografo) obj;
		 return this.getId().equals(other.getId());
	}

	@Override
	public int compareTo(Fotografo o) {
		return this.getCognome().compareTo(o.getCognome());
	}
}
