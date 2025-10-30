package br.com.serratec.dto;

public record ItemPedidoResponseDTO(Long produtoId,String nomeProduto,String descricaoProduto,Double valorUnitario,Integer quantidade,Double desconto,Double subtotal) {}
