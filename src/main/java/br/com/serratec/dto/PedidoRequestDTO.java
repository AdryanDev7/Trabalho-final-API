package br.com.serratec.dto;

import java.util.List;
import br.com.serratec.entity.Cliente;
import br.com.serratec.entity.ItemPedido;

public class PedidoRequestDTO {

    private Cliente cliente;
    private List<ItemPedido> itens;

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(Cliente cliente, List<ItemPedido> itens) {
        this.cliente = cliente;
        this.itens = itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
}
