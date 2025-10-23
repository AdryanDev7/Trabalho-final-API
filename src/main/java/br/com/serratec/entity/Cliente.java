package br.com.serratec.entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;

import br.com.serratec.enums.assinaturaEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID Id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String telefone;
	
	@Email
	private String email;
	
	//private String senha
	@CPF
	private String cpf;
	
	private Endereco endereco;
	
	@Enumerated(EnumType.STRING)
	private assinaturaEnum assinatura;
	
	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Cliente(UUID id, @NotBlank String nome, @NotBlank String telefone, @Email String email, @CPF String cpf,
			Endereco endereco, assinaturaEnum assinatura) {
		super();
		Id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.cpf = cpf;
		this.endereco = endereco;
		this.assinatura = assinatura;
	}

	public UUID getId() {
		return Id;
	}

	public void setId(UUID id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public assinaturaEnum getAssinatura() {
		return assinatura;
	}

	public void setAssinatura(assinaturaEnum assinatura) {
		this.assinatura = assinatura;
	}
	
	
	
	

}
