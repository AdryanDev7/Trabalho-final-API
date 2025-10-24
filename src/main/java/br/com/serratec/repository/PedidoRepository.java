package br.com.serratec.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	public Page<Pedido> findByValorTotalGreaterThan(Double valorTotal, Pageable pageable);
	

}
