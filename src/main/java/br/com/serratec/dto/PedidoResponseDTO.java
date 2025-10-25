package br.com.serratec.dto;

import br.com.serratec.entity.Produto;

public record PedidoResponseDTO (Long id, Double valorTotal, Produto produto ){

}
