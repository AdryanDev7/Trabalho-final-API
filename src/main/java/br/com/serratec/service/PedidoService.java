package br.com.serratec.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.PedidoRequestDTO;
import br.com.serratec.dto.PedidoResponseDTO;
import br.com.serratec.entity.ItemPedido;
import br.com.serratec.entity.Pedido;
import br.com.serratec.repository.ClienteRepository;
import br.com.serratec.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    public PedidoResponseDTO inserir(PedidoRequestDTO dto) {
        if (dto.getCliente() == null) {
            throw new RuntimeException("Cliente não informado.");
        }
        if (dto.getItens() == null) {
            throw new RuntimeException("Nenhum item de pedido informado.");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(dto.getCliente());
        pedido.setItens(dto.getItens());
        pedido.setDataPedido(LocalDate.now());

        for (ItemPedido item : dto.getItens()) {
            item.setPedido(pedido);
            item.setSubtotal(item.getQuantidade() * item.getValorVenda());
        }

        pedido.setValorTotal(calcularValorTotalComDesconto(pedido));
        pedido = repository.save(pedido);

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getValorTotal(),
                pedido.getDataPedido(),
                pedido.getItens()
        );
    }

    public List<PedidoResponseDTO> listar() {
        List<PedidoResponseDTO> pedidosDTO = new ArrayList<>();
        List<Pedido> pedidos = repository.findAll();

        for (Pedido pedido : pedidos) {
            pedidosDTO.add(new PedidoResponseDTO(
                    pedido.getId(),
                    pedido.getValorTotal(),
                    pedido.getDataPedido(),
                    pedido.getItens()
            ));
        }

        return pedidosDTO;
    }

    public PedidoResponseDTO buscarId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getValorTotal(),
                pedido.getDataPedido(),
                pedido.getItens()
        );
    }

    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedidoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));

        pedidoExistente.setItens(dto.getItens());
        pedidoExistente.setCliente(dto.getCliente());

        for (ItemPedido item : dto.getItens()) {
            item.setPedido(pedidoExistente);
            item.setSubtotal(item.getQuantidade() * item.getValorVenda());
        }

        pedidoExistente.setValorTotal(calcularValorTotalComDesconto(pedidoExistente));
        pedidoExistente = repository.save(pedidoExistente);

        return new PedidoResponseDTO(
                pedidoExistente.getId(),
                pedidoExistente.getValorTotal(),
                pedidoExistente.getDataPedido(),
                pedidoExistente.getItens()
        );
    }

    private double calcularValorTotalComDesconto(Pedido pedido) {
        double total = 0;
        for (ItemPedido item : pedido.getItens()) {
            total += item.getSubtotal();
        }
        if (pedido.getCliente() != null && pedido.getCliente().getAssinatura() != null) {
            total *= pedido.getCliente().getAssinatura().getDesconto();
        }
        return total;
    }
}