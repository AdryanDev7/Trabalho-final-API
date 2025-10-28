package br.com.serratec.dto;

import java.util.HashSet;
import java.util.Set;

import br.com.serratec.entity.Cliente;

public class ClienteUpdateDTO {

	private Set<Cliente> clientes = new HashSet<>();

	private String nome;
	private String email;
	private String telefone;

	public ClienteUpdateDTO(Cliente clienteAtualizar) {
		this.nome = clienteAtualizar.getNome();
		this.email = clienteAtualizar.getEmail();
		this.telefone = clienteAtualizar.getTelefone();
	}

	public Set<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
