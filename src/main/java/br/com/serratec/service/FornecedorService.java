package br.com.serratec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.FornecedorRequestDTO;
import br.com.serratec.dto.FornecedorResponseDTO;
import br.com.serratec.entity.Fornecedor;
import br.com.serratec.repository.FornecedorRepository;

@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository repository;

	public FornecedorResponseDTO inserir(FornecedorRequestDTO fornecedorDTO) {
		Optional<Fornecedor> fornecedorOpt = repository.findByCnpj(fornecedorDTO.getCnpj());
		if (fornecedorOpt.isPresent()) {
			throw new RuntimeException("CNPJ já cadastrado!");
		}

		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setNome(fornecedorDTO.getNome());
		fornecedor.setCnpj(fornecedorDTO.getCnpj());
		fornecedor.setEmail(fornecedorDTO.getEmail());
		fornecedor.setTelefone(fornecedorDTO.getTelefone());

		Fornecedor f = repository.save(fornecedor);
		return new FornecedorResponseDTO(f.getId(), f.getNome(), f.getCnpj(), f.getEmail(), f.getTelefone());
	}

	public FornecedorResponseDTO editar(Long id, FornecedorRequestDTO fornecedorDTO) {
		Optional<Fornecedor> fornecedorOpt = repository.findById(id);

		if (fornecedorOpt.isPresent()) {
			Fornecedor fornecedorExistente = fornecedorOpt.get();
			fornecedorExistente.setNome(fornecedorDTO.getNome());
			fornecedorExistente.setEmail(fornecedorDTO.getEmail());
			fornecedorExistente.setTelefone(fornecedorDTO.getTelefone());

			if (!fornecedorExistente.getCnpj().equals(fornecedorDTO.getCnpj())) {
				Optional<Fornecedor> cnpjOpt = repository.findByCnpj(fornecedorDTO.getCnpj());
				if (cnpjOpt.isPresent()) {
					throw new RuntimeException("CNPJ já cadastrado!");
				}
				fornecedorExistente.setCnpj(fornecedorDTO.getCnpj());
			}

			Fornecedor f = repository.save(fornecedorExistente);
			return new FornecedorResponseDTO(f.getId(), f.getNome(), f.getCnpj(), f.getEmail(), f.getTelefone());
		}
		throw new RuntimeException("Fornecedor não encontrado!");
	}

	public void deletar(Long id) {
		Optional<Fornecedor> fornecedorOpt = repository.findById(id);
		if (fornecedorOpt.isEmpty()) {
			throw new RuntimeException("Fornecedor não encontrado com o id!");
		}
		repository.deleteById(id);
	}

	public List<FornecedorResponseDTO> listar() {
		List<Fornecedor> fornecedores = repository.findAll();
		List<FornecedorResponseDTO> dtos = new ArrayList<>();
		for (Fornecedor f : fornecedores) {
			dtos.add(new FornecedorResponseDTO(f.getId(), f.getNome(), f.getCnpj(), f.getEmail(), f.getTelefone()));
		}
		return dtos;
	}

	public FornecedorResponseDTO buscarId(Long id) {
		Optional<Fornecedor> fornecedorOpt = repository.findById(id);
		if (fornecedorOpt.isPresent()) {
			Fornecedor f = fornecedorOpt.get();
			return new FornecedorResponseDTO(f.getId(), f.getNome(), f.getCnpj(), f.getEmail(), f.getTelefone());
		}
		throw new RuntimeException("Fornecedor não encontrado!");
	}

	public Page<FornecedorResponseDTO> listarPorPagina(Pageable pageable) {
		Page<Fornecedor> pageFornecedor = repository.findAll(pageable);
		List<FornecedorResponseDTO> dtos = new ArrayList<>();
		for (Fornecedor f : pageFornecedor.getContent()) {
			dtos.add(new FornecedorResponseDTO(f.getId(), f.getNome(), f.getCnpj(), f.getEmail(), f.getTelefone()));
		}
		return new PageImpl<>(dtos, pageFornecedor.getPageable(), pageFornecedor.getTotalElements());
	}

	public Page<FornecedorResponseDTO> buscarNome(String pNome, Pageable pageable) {
		Page<Fornecedor> pageFornecedor = repository.findByNomeContaining(pNome, pageable);
		List<FornecedorResponseDTO> dtos = new ArrayList<>();
		for (Fornecedor f : pageFornecedor.getContent()) {
			dtos.add(new FornecedorResponseDTO(f.getId(), f.getNome(), f.getCnpj(), f.getEmail(), f.getTelefone()));
		}
		return new PageImpl<>(dtos, pageFornecedor.getPageable(), pageFornecedor.getTotalElements());
	}
}