package br.com.serratec.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErroResposta> handleResourceNotFound(ResourceNotFoundException ex) {
		ErroResposta error = new ErroResposta(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not Found",
				ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErroResposta> handleValidation(ValidationException ex) {
		ErroResposta error = new ErroResposta(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Validation Error",
				ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroResposta> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ErroResposta error = new ErroResposta(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				"Validation Failed", errors.toString());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErroResposta> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		String message = "Erro de integridade do banco de dados";

		if (ex.getMessage().contains("Duplicate entry")) {
			message = "Registro duplicado. Este valor já existe no sistema.";
		}

		ErroResposta error = new ErroResposta(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Database Error",
				message);
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErroResposta> handleEnumError(MethodArgumentTypeMismatchException ex) {
		String message = String.format("Valor inválido '%s' para o campo '%s'", ex.getValue(), ex.getName());

		if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
			Object[] enumConstants = ex.getRequiredType().getEnumConstants();
			message += ". Valores permitidos: " + java.util.Arrays.toString(enumConstants);
		}

		ErroResposta error = new ErroResposta(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				"Invalid Enum Value", message);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErroResposta> handleGeneralException(Exception ex) {
		ErroResposta error = new ErroResposta(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server Error", "Ocorreu um erro inesperado: " + ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
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