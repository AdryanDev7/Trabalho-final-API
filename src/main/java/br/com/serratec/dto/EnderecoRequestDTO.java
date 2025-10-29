package br.com.serratec.dto;

public class EnderecoRequestDTO {
	private Long numero;
	private String complemento;
	
	public EnderecoRequestDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public EnderecoRequestDTO(Long numero, String complemento) {
		super();
		this.numero = numero;
		this.complemento = complemento;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	

}
