package br.com.serratec.entity;
import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;

import br.com.serratec.enums.AssinaturaEnum;
import br.com.serratec.enums.StatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotBlank
	private String nome;

	@NotBlank
	private String telefone;

	@Email
	private String email;

	@CPF
	private String cpf;

	private String senha;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;
	
	@Enumerated(EnumType.STRING)
	private AssinaturaEnum assinatura;

	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	@Override
	public String toString() {
		return "\nNome: " + nome + "\nEmail: " + email;
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

	public AssinaturaEnum getAssinatura() {
		return assinatura;
	}

	public void setAssinatura(AssinaturaEnum assinatura) {
		this.assinatura = assinatura;
	}


	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
}
