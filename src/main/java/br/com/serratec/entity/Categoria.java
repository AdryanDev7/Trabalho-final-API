package br.com.serratec.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Categoria{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@NotBlank
	private String nome;
	@Size (max = 500)
	private String descricao;
	
	public Categoria() {
		// TODO Auto-generated constructor stub
	}
	
	public Categoria(Long Id, @NotBlank String nome, @Size(max = 500) String descricao) {
		super();
		this.Id = Id;
		this.nome = nome;
		this.descricao = descricao;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		this.Id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
