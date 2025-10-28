package br.com.serratec.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.serratec.entity.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	Page<Fornecedor> findByNomeContaining(String nome, Pageable pageable);
	Optional<Fornecedor> findByCnpj(String cnpj);
}