package it.uniroma3.siw.progetto.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Foto implements Comparable<Foto> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private LocalDateTime data;
	
	@ManyToOne
	private Fotografo fotografo;
	@ManyToOne
	private Album album;
	
	public Foto() {
		this.data = LocalDateTime.now();
	}
	
	public Foto(String nome, Album album) {
		this();
		this.nome = nome;
		this.fotografo = album.getFotografo();
		this.album = album;
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
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public Fotografo getFotografo() {
		return fotografo;
	}
	public void setFotografo(Fotografo fotografo) {
		this.fotografo = fotografo;
	}
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((fotografo == null) ? 0 : fotografo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Foto other = (Foto) obj;
		return this.getId().equals(other.getId());
	}

	@Override
	public int compareTo(Foto o) {
		return this.getNome().compareTo(o.getNome());
	}
	
	

}
