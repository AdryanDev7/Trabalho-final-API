package br.com.serratec.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario implements UserDetails{

	// UserDetails serve para encapsular informações de um usuário de forma
		// padronizada para o framework Spring Security. Ele armazena dados essenciais
		// como nome de usuário, senha e autorizações (perfis), sendo utilizado para
		// autenticação e autorização no sistema.
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String senha;
	
	// usa o set para não repetir usando o hashSet
	// fetch quando se tem um volume muito grande de registro
	@OneToMany(mappedBy = "id.usuario", fetch = FetchType.EAGER)
	private Set<UsuarioPerfil> usuarioPerfis = new HashSet<>() ;

			
	public Set<UsuarioPerfil> getUsuarioPerfis() {
		return usuarioPerfis;
	}

	
	@Override
	public String toString() {
		return "Id:" +id + "\n Nome:" + nome + "\nEmail:"+ email;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	@Override
	//passando perfis, cada usuario tem uma lista de perfis
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (UsuarioPerfil usuarioPerfil : usuarioPerfis) {
			authorities.add(new SimpleGrantedAuthority(usuarioPerfil.getPerfil().getNome()));
		}
		
			
		return authorities;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

}
