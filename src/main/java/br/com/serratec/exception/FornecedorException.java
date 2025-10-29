package br.com.serratec.exception;

public class FornecedorException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public FornecedorException(String message) {
		super(message);
	}

}
