package br.com.serratec.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.PedidoRequestDTO;
import br.com.serratec.dto.PedidoResponseDTO;
import br.com.serratec.dto.ItemPedidoResponseDTO;
import br.com.serratec.entity.ItemPedido;
import br.com.serratec.entity.Pedido;
import br.com.serratec.entity.Produto;
import br.com.serratec.exception.ClienteException;
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
                .orElseThrow(() -> new ClienteException("Cliente não encontrado."));

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

        double total = 0;
        for (ItemPedido item : pedido.getItens()) {
            total += item.getSubtotal();
        }
        if (pedido.getCliente().getAssinatura() != null) {
            total *= pedido.getCliente().getAssinatura().getDesconto();
        }
        pedido.setValorTotal(total);

        pedido = repository.save(pedido);

        List<ItemPedidoResponseDTO> itensDTO = new ArrayList<>();
        for (ItemPedido item : pedido.getItens()) {
            double valorAposDesconto = item.getSubtotal() - item.getDesconto();
            itensDTO.add(new ItemPedidoResponseDTO(
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getProduto().getDescricao(),
                    item.getValorVenda(),
                    item.getQuantidade(),
                    item.getDesconto(),
                    item.getSubtotal())
            );
        }

        return new PedidoResponseDTO(pedido.getId(), pedido.getDataPedido(), pedido.getValorTotal(), 
        		pedido.getCliente().getAssinatura().name(), itensDTO);

    }

    public List<PedidoResponseDTO> listar() {
        List<PedidoResponseDTO> pedidosDTO = new ArrayList<>();
        List<Pedido> pedidos = repository.findAll();

        for (Pedido pedido : pedidos) {
            List<ItemPedidoResponseDTO> itensDTO = new ArrayList<>();
            for (ItemPedido item : pedido.getItens()) {
                double valorAposDesconto = item.getSubtotal() - item.getDesconto();
                itensDTO.add(new ItemPedidoResponseDTO(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getProduto().getDescricao(),
                        item.getValorVenda(),
                        item.getQuantidade(),
                        item.getDesconto(),
                        item.getSubtotal())
                );
            }
            pedidosDTO.add(new PedidoResponseDTO(pedido.getId(),pedido.getDataPedido(),pedido.getValorTotal(),
                    pedido.getCliente().getAssinatura().name(),itensDTO));
        }

        return pedidosDTO;
    }

    public PedidoResponseDTO buscarId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));

        List<ItemPedidoResponseDTO> itensDTO = new ArrayList<>();
        for (ItemPedido item : pedido.getItens()) {
            double valorAposDesconto = item.getSubtotal() - item.getDesconto();
            itensDTO.add(new ItemPedidoResponseDTO(
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getProduto().getDescricao(),
                    item.getValorVenda(),
                    item.getQuantidade(),
                    item.getDesconto(),
                    item.getSubtotal())
            );
        }

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getValorTotal(),
                pedido.getCliente().getAssinatura().name(),
                itensDTO
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

        double total = 0;
        for (ItemPedido item : pedidoExistente.getItens()) {
            total += item.getSubtotal();
        }
        if (pedidoExistente.getCliente().getAssinatura() != null) {
            total *= pedidoExistente.getCliente().getAssinatura().getDesconto();
        }
        pedidoExistente.setValorTotal(total);

        pedidoExistente = repository.save(pedidoExistente);

        List<ItemPedidoResponseDTO> itensDTO = new ArrayList<>();
        for (ItemPedido item : pedidoExistente.getItens()) {
            double valorAposDesconto = item.getSubtotal() - item.getDesconto();
            itensDTO.add(new ItemPedidoResponseDTO(
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getProduto().getDescricao(),
                    item.getValorVenda(),
                    item.getQuantidade(),
                    item.getDesconto(),
                    item.getSubtotal())
            );
        }

        return new PedidoResponseDTO(pedidoExistente.getId(),pedidoExistente.getDataPedido(),pedidoExistente.getValorTotal(),
                pedidoExistente.getCliente().getAssinatura().name(),itensDTO);
    }
}
