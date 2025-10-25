package br.com.serratec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.entity.Pedido;
import br.com.serratec.entity.Produto;
import br.com.serratec.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	
	public Pedido inserir(Pedido pedido) {
		double total = calcularValorTotalComDesconto(pedido);
		pedido.setValorTotal(total);
		return repository.save(pedido);
	}

	public List<Pedido> listar() {
		return repository.findAll();
	}

	public Pedido buscarId(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado")); // pedido não
																											// encontrado
																											// exception
	}

	
	public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
		Pedido pedidoExistente = buscarId(id); // esse aqui já tá ali em cima
		pedidoExistente.setProduto(pedidoAtualizado.getProduto()); // pedido sem produto exception
		pedidoExistente.setCliente(pedidoAtualizado.getCliente()); // cliente nao informado exception
		pedidoExistente.setValorTotal(calcularValorTotalComDesconto(pedidoAtualizado)); // valor invalido exception?
		return repository.save(pedidoExistente);
	}

	private double calcularValorTotalComDesconto(Pedido pedido) {
		if (pedido.getProduto() != null) {// aqui seria outro pedido vazio exception

			double total = 0;
			for (Produto produto : pedido.getProduto()) {
				total = total + produto.getValor();
			}

			if (pedido.getCliente().getAssinatura() != null) { // fidelidade não informada exception?
				var desconto = pedido.getCliente().getAssinatura().getDesconto();
				total = total * desconto;

			}

			return total;
		}
		return 0;
	}

	public Page<Pedido> buscarPorValorMinimo(Double valorTotal, Pageable pageable) {
		return repository.findByValorTotalGreaterThan(valorTotal, pageable); // nenhum pedido encontrado exception
	}

}
