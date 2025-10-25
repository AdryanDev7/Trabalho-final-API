package br.com.serratec.dto;

import java.time.LocalDate;
import java.util.List;
import br.com.serratec.entity.ItemPedido;

public record PedidoResponseDTO(Long id, Double valorTotal,LocalDate dataPedido, List<ItemPedido> itens) { 

}
