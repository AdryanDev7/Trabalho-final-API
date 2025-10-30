package br.com.serratec.dto;

import java.time.LocalDate;
import java.util.List;

public record PedidoResponseDTO(Long pedidoId,LocalDate dataPedido,Double ValorPedido,String tipoAssinatura,List<ItemPedidoResponseDTO> itens) {}

