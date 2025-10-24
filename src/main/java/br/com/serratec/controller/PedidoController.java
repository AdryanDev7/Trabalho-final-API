package br.com.serratec.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.entity.Pedido;
import br.com.serratec.service.PedidoService;


@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public ResponseEntity<Pedido> inserir(@RequestBody Pedido pedido) {
        Pedido novoPedido = service.inserir(pedido);
        return ResponseEntity.ok(novoPedido);
    }
    
    @GetMapping
    public ResponseEntity<List<Pedido>> listar(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC)  Pageable pageable) {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("{id}")
    public ResponseEntity<Pedido> buscarId(@PathVariable Long id) {
        Pedido pedido = service.buscarId(id);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
        Pedido pedido = service.atualizar(id, pedidoAtualizado);
        return ResponseEntity.ok(pedido);
    }
    
    @GetMapping("/buscarValorMinimo")
	public ResponseEntity <Page<Pedido>> findByValorTotalGreaterThan(@RequestParam(defaultValue = "")Double valorTotal, Pageable pageable){
		return ResponseEntity.ok(service.buscarPorValorMinimo(valorTotal, pageable));

	}

   
}