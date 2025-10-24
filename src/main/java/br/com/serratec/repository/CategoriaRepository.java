package br.com.serratec.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
	Page<Categoria> findByNomeContaining(String nome, Pageable pageable);
	Page<Categoria> findByDescricaoContaining(String descricao, Pageable pageable);
	Optional<Categoria> findByNome(String nome);
}
