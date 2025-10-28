package br.com.serratec.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;

import br.com.serratec.entity.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClienteRequestDTO {

	private Set<Cliente> clientes = new HashSet<>();

	private UUID id;

	@NotBlank
	private String nome;

	@CPF
	@UniqueElements
	private String cpf;

	@Email
	@UniqueElements
	private String email;

	public ClienteRequestDTO() {
	}

	public Set<Cliente> getCliente() {
		return clientes;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
