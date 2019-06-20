package it.uniroma3.siw.progetto.models;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Foto implements Comparable<Foto>, MultipartFile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private LocalDateTime data;
	private byte[] bytes;
	
	@ManyToOne
	private Fotografo fotografo;
	@ManyToOne
	private Album album;
	
	public Foto() {
		this.data = LocalDateTime.now();
	}
	
	public Foto(String nome) {
		this();
		this.nome = nome;
	}
	
	public Foto(String nome, Fotografo fotografo) {
		this(nome);
		this.fotografo = fotografo;
	}
	public Foto(String nome, Album album) {
		this(nome, album.getFotografo());
		this.album = album;
	}
	
	public Foto(String nome, Album album, byte[] content) {
		this(nome, album);
		this.setBytes(content);
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

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
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

	@Override
	public String getName() {
		return this.nome;
	}

	@Override
	public String getOriginalFilename() {
		return this.nome;
	}

	@Override
	public String getContentType() {
		return "image/jpeg";
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public long getSize() {
		long b = 0;
		try {
			b = this.getBytes().length;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return this.bytes;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		// TODO Auto-generated method stub
		
	}
	
	

}
