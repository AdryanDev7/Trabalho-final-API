package br.com.serratec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.ClienteResponseDTO;
import br.com.serratec.dto.ClienteUpdateDTO;
import br.com.serratec.entity.Cliente;
import br.com.serratec.exception.ClienteException;
import br.com.serratec.repository.ClienteRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public List<ClienteResponseDTO> listar() {
		List<ClienteResponseDTO> clienteDTO = new ArrayList<>();

		for (Cliente cliente : repository.findAll()) {
			clienteDTO.add(new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getEmail()));
		}
		return clienteDTO;
	}

	public Cliente inserirCliente(Cliente cliente) {
		return repository.save(cliente);
	}
	
	@Transactional
	public ClienteResponseDTO atualizarCliente(UUID id, ClienteUpdateDTO update) {
		Cliente clienteAtualizar = repository.findById(id)
				.orElseThrow(() -> new ClienteException("Cliente não encontrado com o ID: " + id));

		if (update.getNome() != null && !update.getNome().trim().isEmpty()) {
			clienteAtualizar.setNome(update.getNome());
		}

		if (update.getEmail() != null && !update.getEmail().trim().isEmpty()) {
			clienteAtualizar.setEmail(update.getEmail());
		}

		if (update.getTelefone() != null && !update.getTelefone().trim().isEmpty()) {
			clienteAtualizar.setTelefone(update.getTelefone());
		}

		repository.save(clienteAtualizar);
		return new ClienteResponseDTO(clienteAtualizar);
	}

}
