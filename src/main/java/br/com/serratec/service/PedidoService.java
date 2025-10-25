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
import br.com.serratec.entity.Pedido;
import br.com.serratec.entity.Produto;
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
        if (dto.getCliente() == null) {
            throw new RuntimeException("Cliente não informado.");
        }
        if (dto.getProduto() == null) {
            throw new RuntimeException("Nenhum produto informado.");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(dto.getCliente());
        pedido.setProduto(dto.getProduto());
        pedido.setDataPedido(LocalDate.now());

        double total = calcularValorTotalComDesconto(pedido);
        pedido.setValorTotal(total);

        pedido = repository.save(pedido);

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getValorTotal(),
                pedido.getDataPedido(),
                pedido.getProduto()
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
                    pedido.getProduto()
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
                pedido.getProduto()
        );
    }

    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedidoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));

        pedidoExistente.setProduto(dto.getProduto());
        pedidoExistente.setCliente(dto.getCliente());
        pedidoExistente.setValorTotal(calcularValorTotalComDesconto(pedidoExistente));

        pedidoExistente = repository.save(pedidoExistente);

        return new PedidoResponseDTO(
                pedidoExistente.getId(),
                pedidoExistente.getValorTotal(),
                pedidoExistente.getDataPedido(),
                pedidoExistente.getProduto()
        );
    }

    private double calcularValorTotalComDesconto(Pedido pedido) {
        double total = 0;

        for (Produto produto : pedido.getProduto()) {
            total += produto.getValor();
        }

        if (pedido.getCliente() != null && pedido.getCliente().getAssinatura() != null) {
            double desconto = pedido.getCliente().getAssinatura().getDesconto();
            total = total * desconto;
        }

        return total;
    }

}
