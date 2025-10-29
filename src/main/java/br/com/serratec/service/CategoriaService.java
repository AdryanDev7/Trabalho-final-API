package br.com.serratec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.CategoriaRequestDTO;
import br.com.serratec.dto.CategoriaResponseDTO;
import br.com.serratec.entity.Categoria;
import br.com.serratec.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;


    public CategoriaResponseDTO inserir(CategoriaRequestDTO dto) {
        if (dto.getNome() == null) {
            throw new RuntimeException("Nome não informado.");
        }
        if (dto.getDescricao() == null) {
            throw new RuntimeException("Descrição não informada.");
        }

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        categoria = repository.save(categoria);

        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }

    public List<CategoriaResponseDTO> listar() {
        List<CategoriaResponseDTO> ResponseDTO = new ArrayList<>();
        List<Categoria> categorias = repository.findAll();

        for (Categoria categoria : categorias) {
        	CategoriaResponseDTO dto = new CategoriaResponseDTO(categoria.getId(),
        			categoria.getNome(),
        			categoria.getDescricao());
        	ResponseDTO.add(dto);

        }

        return ResponseDTO;
    }

    public CategoriaResponseDTO buscarId(Long id) {
    	Optional<Categoria> categorias = Optional.of(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrado.")));
    	
        Categoria categoria = categorias.get();
        return new CategoriaResponseDTO(categoria.getId(),categoria.getNome(),categoria.getDescricao());
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        Optional<Categoria> categorias = Optional.of(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrado.")));

        Categoria categoria = categorias.get();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categoria atualizada = repository.save(categoria);

        return new CategoriaResponseDTO(
        		atualizada.getId(),
        		atualizada.getNome(),
        		atualizada.getDescricao()
        );
    }

}