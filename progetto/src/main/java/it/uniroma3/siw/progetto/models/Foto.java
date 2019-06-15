package it.uniroma3.siw.progetto.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Foto {
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
		//no op
	}
	
	public Foto(String nome, Fotografo fotografo, Album album) {
		super();
		this.nome = nome;
		this.data = LocalDateTime.now();
		this.fotografo = fotografo;
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

}
