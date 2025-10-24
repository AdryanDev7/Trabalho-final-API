package br.com.serratec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.CategoriaRequestDTO;
import br.com.serratec.dto.CategoriaResponseDTO;
import br.com.serratec.entity.Categoria;
import br.com.serratec.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public List<CategoriaResponseDTO> listar() {
		List<CategoriaResponseDTO> categoriasDTO = new ArrayList<>();
		for (Categoria categoria : repository.findAll()) {
			categoriasDTO
					.add(new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao()));
		}
		return categoriasDTO;
	}

	public Page<Categoria> listarPorPagina(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Categoria> buscarNome(String pNome, Pageable pageable) {
		return repository.findByNomeContaining(pNome, pageable);
	}

	public CategoriaResponseDTO inserir(CategoriaRequestDTO categoriaRequestDTO) {

		Optional<Categoria> categoriaOpt = repository.findByNome(categoriaRequestDTO.getNome());

		if (categoriaOpt.isPresent()) {
			throw new RuntimeException("Uma categoria com este nome já existe.");
		}

		Categoria categoria = new Categoria();
		categoria.setNome(categoriaRequestDTO.getNome());
		categoria.setDescricao(categoriaRequestDTO.getDescricao());

		categoria = repository.save(categoria);

		return new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
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