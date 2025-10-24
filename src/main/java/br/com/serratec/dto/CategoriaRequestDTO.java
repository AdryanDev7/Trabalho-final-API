package br.com.serratec.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDTO {

	@NotBlank
	private String nome;
	@NotBlank
	private String descricao;

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
