package br.com.serratec.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.serratec.dto.EnderecoResponseDTO;
import br.com.serratec.entity.Endereco;
import br.com.serratec.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;

	public EnderecoResponseDTO buscarCep(String cep) {
		Optional<Endereco> endereco = repository.findByCep(cep);
		if (endereco.isPresent()) {
			return new EnderecoResponseDTO(endereco.get().getCep(), endereco.get().getLogradouro(),
					endereco.get().getNumero(), endereco.get().getBairro(), endereco.get().getLocalidade(),
					endereco.get().getUf());
		} else {
			RestTemplate template = new RestTemplate();
			String url = "https://viacep.com.br/ws/" + cep + "/json/";
			Optional<Endereco> viaCep = Optional.ofNullable(template.getForObject(url, Endereco.class));

			if (endereco.get().getCep() != null) {
				String cepFormatado = viaCep.get().getCep().replaceAll("-", "");
				viaCep.get().setCep(cepFormatado);
				return inserir(viaCep.get());
			} else {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
			}
		}
	}

	private EnderecoResponseDTO inserir(Endereco endereco) {
		endereco = repository.save(endereco);
		return new EnderecoResponseDTO(endereco.getCep(), endereco.getLogradouro(), endereco.getNumero(),
				endereco.getBairro(), endereco.getLocalidade(), endereco.getUf());
	}
}
