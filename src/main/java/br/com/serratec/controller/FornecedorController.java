package br.com.serratec.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.dto.FornecedorRequestDTO;
import br.com.serratec.dto.FornecedorResponseDTO;
import br.com.serratec.service.FornecedorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

	@Autowired
	private FornecedorService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FornecedorResponseDTO inserir(@Valid @RequestBody FornecedorRequestDTO fornecedorDTO) {
		return service.inserir(fornecedorDTO);
	}

	@PutMapping("/{id}")
	public FornecedorResponseDTO editar(@PathVariable Long id, @Valid @RequestBody FornecedorRequestDTO fornecedorDTO) {
		return service.editar(id, fornecedorDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		service.deletar(id);
	}

	@GetMapping
	public List<FornecedorResponseDTO> listar() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public FornecedorResponseDTO buscarPorId(@PathVariable Long id) {
		return service.buscarId(id);
	}

	@GetMapping("/paginacao")
	public Page<FornecedorResponseDTO> listarPorPagina(
			@PageableDefault(page = 0, size = 10, sort = "nome", direction = Direction.ASC) Pageable pageable) {
		return service.listarPorPagina(pageable);
	}

	@GetMapping("/buscarNome")
	public Page<FornecedorResponseDTO> buscarNome(@RequestParam(defaultValue = "") String pNome, Pageable pageable) {
		return service.buscarNome(pNome, pageable);
	}
}