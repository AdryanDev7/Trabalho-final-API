package br.com.serratec.dto;

import java.util.List;

import br.com.serratec.entity.Cliente;
import br.com.serratec.entity.Produto;

public class PedidoRequestDTO {

    private Cliente cliente;
    private List<Produto> produto;

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(Cliente cliente, List<Produto> produto) {
        this.cliente = cliente;
        this.produto = produto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Produto> getProduto() {
        return produto;
    }

    public void setProduto(List<Produto> produto) {
        this.produto = produto;
    }
}
