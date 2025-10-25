package br.com.serratec.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.entity.Categoria;
import br.com.serratec.service.CategoriaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Categoria inserir(@Valid @RequestBody Categoria categoria) {
		return service.inserir(categoria);
	}

	@PutMapping("/{id}")
	public Categoria editar(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {
		return service.editar(id, categoria);
	}

	@GetMapping
	public List<Categoria> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public Categoria buscarPorId(@PathVariable Long id) {
		return service.buscarId(id);
	}

	@GetMapping("/paginacao")
	public Page<Categoria> listarPorPagina(
			@PageableDefault(page = 0, size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable) {
		return service.listarPorPagina(pageable);
	}

	@GetMapping("/buscarNome")
	public Page<Categoria> buscarNome(@RequestParam(defaultValue = "") String pNome, Pageable pageable) {
		return service.buscarNome(pNome, pageable);
	}
}