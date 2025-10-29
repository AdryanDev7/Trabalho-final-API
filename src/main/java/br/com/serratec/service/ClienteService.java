package br.com.serratec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.config.MailConfig;
import br.com.serratec.dto.ClienteResponseDTO;
import br.com.serratec.dto.ClienteUpdateDTO;
import br.com.serratec.dto.EnderecoResponseDTO;
import br.com.serratec.entity.Cliente;
import br.com.serratec.entity.Endereco;
import br.com.serratec.exception.ClienteException;
import br.com.serratec.repository.ClienteRepository;
import jakarta.transaction.Transactional;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private MailConfig mailConfig;

	public List<ClienteResponseDTO> listar() {
	    List<ClienteResponseDTO> clientesDTO = new ArrayList<>();
	    for (Cliente c : repository.findAll()) {
	        clientesDTO.add(new ClienteResponseDTO(c.getId(),c.getNome(),c.getEmail(),c.getTelefone()));
	    }
	    return clientesDTO;
	}


	public Cliente inserirCliente(Cliente cliente) {
		mailConfig.enviarEmail(cliente.getEmail(), "Cadastro atualizado com sucesso", cliente.toString());
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
		mailConfig.enviarEmail(clienteAtualizar.getEmail(), "Cadastro atualizado com sucesso",
				clienteAtualizar.toString());
		return new ClienteResponseDTO(clienteAtualizar);
	}

}
