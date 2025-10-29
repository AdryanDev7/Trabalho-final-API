package br.com.serratec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.entity.Perfil;
import br.com.serratec.service.PerfilService;

@RestController
@RequestMapping("/perfis")
public class PerfilController {
	
	@Autowired
	private PerfilService service;

	@GetMapping("{id}")
	public ResponseEntity<Perfil> buscar(@PathVariable Long id) {
		Perfil perfil = service.buscar(id);
		if (perfil != null) {
			return ResponseEntity.ok(perfil);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Perfil inserir(@RequestBody Perfil perfil) {
		return service.inserir(perfil);
	}

}
