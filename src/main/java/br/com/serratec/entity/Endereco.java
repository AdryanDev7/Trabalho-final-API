package br.com.serratec.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Endereco {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String cep;
	
	@NotBlank
	private String logradouro;
	
	private String numero;
	
	@NotBlank
	private String cidade;
	
	@NotBlank
	private String uf;
	
	public Endereco() {
		// TODO Auto-generated constructor stub
	}

	
	public Endereco(Long id, @NotBlank String cep, @NotBlank String logradouro, String numero, @NotBlank String cidade,
			@NotBlank String uf) {
		super();
		this.id = id;
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.cidade = cidade;
		this.uf = uf;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	
}