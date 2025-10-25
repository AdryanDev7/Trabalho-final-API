package br.com.serratec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.dto.ClienteRequestDTO;
import br.com.serratec.dto.ClienteResponseDTO;
import br.com.serratec.entity.Cliente;
import br.com.serratec.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@PostMapping
	@ResponseStatus
	public Cliente inserirCliente(@RequestBody Cliente cliente) {
		return service.inserirCliente();
	}

	@PutMapping("{/id}")
	@ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
	public ResponseEntity<Cliente> atualizarCliente(@Valid @PathVariable @RequestBody ClienteRequestDTO clienteDTO){
		ClienteResponseDTO responseDTO = service.atualizarCliente();
		return ResponseEntity.ok(responseDTO);
	}
	

	@GetMapping
	public List<Cliente> listar() {
		return service.listar();
	}

	@GetMapping("{/id}")
	public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id) {
		Cliente cliente = service.buscarCliente(id);
		if (cliente != null) {
			return ResponseEntity.ok(cliente);
		}
		return ResponseEntity.notFound().build();
	}

}
