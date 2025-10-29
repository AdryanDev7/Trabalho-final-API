package br.com.serratec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.ProdutoRequestDTO;
import br.com.serratec.dto.ProdutoResponseDTO;
import br.com.serratec.entity.Categoria;
import br.com.serratec.entity.Produto;
import br.com.serratec.exception.ProdutoException;
import br.com.serratec.repository.CategoriaRepository;
import br.com.serratec.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProdutoResponseDTO> listar() {
        List<Produto> produtos = repository.findAll();
        List<ProdutoResponseDTO> listaDTO = new ArrayList<>();
        for (Produto produto : produtos) {
            listaDTO.add(new ProdutoResponseDTO(produto.getId(),produto.getNome(),produto.getDescricao(),produto.getValor()));
        }
        return listaDTO;
    }

    public ProdutoResponseDTO inserir(ProdutoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoria())
                .orElseThrow(() -> new ProdutoException("Categoria não encontrada"));

        Produto novoProduto = new Produto();
        novoProduto.setNome(dto.getNome());
        novoProduto.setDescricao(dto.getDescricao());
        novoProduto.setValor(dto.getValor());
        novoProduto.setCategoria(categoria);

        Produto salvo = repository.save(novoProduto);

        return new ProdutoResponseDTO(salvo.getId(),salvo.getNome(),salvo.getDescricao(),salvo.getValor());
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Optional<Produto> opt = repository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }

        Produto produto = opt.get();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setValor(dto.getValor());

        Categoria categoria = categoriaRepository.findById(dto.getCategoria())
                .orElseThrow(() -> new ProdutoException("Categoria não encontrada"));
        produto.setCategoria(categoria);

        Produto atualizado = repository.save(produto);

        return new ProdutoResponseDTO(atualizado.getId(),atualizado.getNome(),atualizado.getDescricao(),atualizado.getValor());
    }
}
