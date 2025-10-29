package br.com.serratec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProdutoRequestDTO {

    @NotBlank
    private String nome;

    @Size(max = 500)
    private String descricao;

    @NotNull
    private Double valor;

    @NotNull
    private Long categoria;

    public ProdutoRequestDTO() {
    }

    public ProdutoRequestDTO(String nome, String descricao, Double valor, Long idCategoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = idCategoria;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long idCategoria) {
        this.categoria = idCategoria;
    }
}

