package br.com.serratec.dto;

import java.util.UUID;

import br.com.serratec.entity.Cliente;

public class ClienteResponseDTO {

	private UUID id;

	private String nome;

	private String email;

	private String telefone;

	public ClienteResponseDTO(Cliente clienteAtualizar) {
		this.id = clienteAtualizar.getId();
		this.nome = clienteAtualizar.getNome();
		this.email = clienteAtualizar.getEmail();
		this.telefone = clienteAtualizar.getTelefone();
	}

	public ClienteResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	public UUID getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefone() {
		return telefone;
	}

}
