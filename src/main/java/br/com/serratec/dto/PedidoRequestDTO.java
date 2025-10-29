package br.com.serratec.dto;

import java.util.List;

public class PedidoRequestDTO {

    private String clienteId;
    private List<ItemPedidoDTO> itens;

    public static class ItemPedidoDTO {
        private Long produtoId;
        private Integer quantidade;
        private Double desconto; 

        public Long getProdutoId() {
            return produtoId;
        }
        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }
        public Integer getQuantidade() {
            return quantidade;
        }
        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }
        public Double getDesconto() {
            return desconto;
        }
        public void setDesconto(Double desconto) {
            this.desconto = desconto;
        }
    }

    public String getClienteId() {
        return clienteId;
    }
    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }
    public List<ItemPedidoDTO> getItens() {
        return itens;
    }
    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}
