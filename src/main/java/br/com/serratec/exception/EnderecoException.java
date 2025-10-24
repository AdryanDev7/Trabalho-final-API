package br.com.serratec.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

public class EnderecoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EnderecoException(String message) {
		super(message);
	}

	@ExceptionHandler(HttpClientErrorException.class)
	protected ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		ErroResposta erroResposta = new ErroResposta(HttpStatus.NOT_FOUND.value(),
				"Verifique o CEP informado e tente novamente!", LocalDateTime.now(), erros);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResposta);
	}

}
