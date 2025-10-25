package br.com.serratec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.entity.Categoria;
import br.com.serratec.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public List<Categoria> listar() {
		return repository.findAll();
	}

	public Page<Categoria> listarPorPagina(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Categoria> buscarNome(String pNome, Pageable pageable) {
		return repository.findByNomeContaining(pNome, pageable);
	}

	public Categoria inserir(Categoria categoria) {

		Optional<Categoria> categoriaOpt = repository.findByNome(categoria.getNome());

		if (categoriaOpt.isPresent()) {
			throw new RuntimeException("Uma categoria com este nome já existe.");
		}

		return repository.save(categoria);
	}

	public Categoria editar(Long id, Categoria c) {
		Optional<Categoria> categoria = repository.findById(id);

		if (categoria.isPresent()) {
			Categoria categoriaExistente = categoria.get();
			categoriaExistente.setNome(c.getNome());
			categoriaExistente.setDescricao(c.getDescricao());
			return repository.save(categoriaExistente);
		}

		throw new RuntimeException("Categoria não encontrada com o id!");
	}

	public Categoria buscarId(Long id) {
		Optional<Categoria> categoriaOpt = repository.findById(id);

		if (categoriaOpt.isPresent()) {
			return categoriaOpt.get();
		}

		throw new RuntimeException("Categoria não encontrada com o id!");
	}
}