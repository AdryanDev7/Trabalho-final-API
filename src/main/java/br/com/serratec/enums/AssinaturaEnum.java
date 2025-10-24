package br.com.serratec.enums;

public enum AssinaturaEnum {
	SEM_ASSINATURA(1), STANDARD(0.95), PREMIUM(0.90), PERSONNALITÉ(0.85);
	
    private final double desconto;
    
    AssinaturaEnum(double desconto) {
    	this.desconto = desconto;
    }

	public double getDesconto() {
		return desconto;
	}
    
}
