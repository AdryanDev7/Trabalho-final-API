package br.com.serratec.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FornecedorRequestDTO {

	@NotBlank(message = "Nome não pode ser vazio")
	@Size(max = 60)
	private String nome;

	@NotBlank(message = "CNPJ não pode ser vazio")
	@Size(min = 14, max = 14, message = "CNPJ deve ter 14 dígitos")
	private String cnpj;

	@Email(message = "Email inválido")
	@Size(max = 100)
	private String email;

	@Size(max = 15)
	private String telefone;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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