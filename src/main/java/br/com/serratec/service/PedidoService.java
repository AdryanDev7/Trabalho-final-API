package br.com.serratec.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.PedidoRequestDTO;
import br.com.serratec.dto.PedidoResponseDTO;
import br.com.serratec.entity.ItemPedido;
import br.com.serratec.entity.Pedido;
import br.com.serratec.entity.Produto;
import br.com.serratec.entity.Cliente;
import br.com.serratec.repository.ClienteRepository;
import br.com.serratec.repository.PedidoRepository;
import br.com.serratec.repository.ProdutoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public PedidoResponseDTO inserir(PedidoRequestDTO dto) {
        if (dto.getClienteId() == null) {
            throw new RuntimeException("Cliente não informado.");
        }
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new RuntimeException("Nenhum item de pedido informado.");
        }

        Cliente cliente = clienteRepository.findById(UUID.fromString(dto.getClienteId()))
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDate.now());

        List<ItemPedido> itensPedido = new ArrayList<>();
        for (PedidoRequestDTO.ItemPedidoDTO itemDTO : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoId()));
            
            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setDesconto(itemDTO.getDesconto() != null ? itemDTO.getDesconto() : 0.0);
            item.setPedido(pedido);
            item.setValorVenda(produto.getValor());
            item.setSubtotal(produto.getValor() * item.getQuantidade() - item.getDesconto());

            itensPedido.add(item);
        }

        pedido.setItens(itensPedido);
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

        Cliente cliente = clienteRepository.findById(UUID.fromString(dto.getClienteId()))
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        pedidoExistente.setCliente(cliente);

        List<ItemPedido> itensPedido = new ArrayList<>();
        for (PedidoRequestDTO.ItemPedidoDTO itemDTO : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoId()));

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setDesconto(itemDTO.getDesconto() != null ? itemDTO.getDesconto() : 0.0);
            item.setPedido(pedidoExistente);

            item.setValorVenda(produto.getValor());
            item.setSubtotal(produto.getValor() * item.getQuantidade() - item.getDesconto());

            itensPedido.add(item);
        }

        pedidoExistente.setItens(itensPedido);
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
