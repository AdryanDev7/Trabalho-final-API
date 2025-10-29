package br.com.serratec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.serratec.dto.PedidoRequestDTO;
import br.com.serratec.dto.PedidoResponseDTO;
import br.com.serratec.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarId(id));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> inserir(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        PedidoResponseDTO novoPedido = service.inserir(pedidoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }



    @PutMapping("{id}")
    public ResponseEntity<PedidoResponseDTO> atualizar(@PathVariable Long id, @RequestBody PedidoRequestDTO PedidoRequestDTO) {
        PedidoResponseDTO pedidoAtualizado = service.atualizar(id, PedidoRequestDTO);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    
}