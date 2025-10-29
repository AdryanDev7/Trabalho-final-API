package br.com.serratec.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.entity.Produto;
import br.com.serratec.exception.UsuarioException;
import br.com.serratec.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private ProdutoRepository repository;

	@Autowired

	public List<Produto> listar() {
		List<Produto> produtoLista = new ArrayList<>();

		for (Produto produto : repository.findAll()) {
			produtoLista.add(new Produto(produto.getId(), produto.getNome(), produto.getCategoria()));
		}
		return produtoLista;
	}

	public Produto inserirProduto(Produto produto) {
		return repository.save(produto);
	}

	@Transactional
	public Produto atualizarProduto(Long id, Produto update) {
		Produto produtoAtualizar = repository.findById(id)
				.orElseThrow(() -> new UsuarioException("Produto não encontrado com o ID: " + id));

		if (update.getNome() != null && !update.getNome().trim().isEmpty()) {
			produtoAtualizar.setNome(update.getNome());
		}

		if (update.getDescricao() != null && !update.getDescricao().trim().isEmpty()) {
			produtoAtualizar.setDescricao(update.getDescricao());
		}

		if (update.getValor() != null) {
			produtoAtualizar.setValor(update.getValor());
		}

		return repository.save(produtoAtualizar);
	}

}
