package br.com.serratec.entity;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private Double valorTotal;
	
	private Produto produto;
	
	private Cliente cliente;

	
	public Pedido() {
		// TODO Auto-generated constructor stub
	}
	
	public Pedido(Long id, Double valorTotal, Produto produto, Cliente cliente) {
		super();
		Id = id;
		this.valorTotal = valorTotal;
		this.produto = produto;
		this.cliente = cliente;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
