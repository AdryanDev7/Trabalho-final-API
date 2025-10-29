package br.com.serratec.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.serratec.config.MailConfig;
import br.com.serratec.dto.UsuarioRequestDTO;
import br.com.serratec.dto.UsuarioResponseDTO;
import br.com.serratec.entity.Usuario;
import br.com.serratec.entity.UsuarioPerfil;
import br.com.serratec.exception.UsuarioException;
import br.com.serratec.repository.UsuarioPerfilRepository;
import br.com.serratec.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private MailConfig mailConfig;

	public List<UsuarioResponseDTO> listar() {
		List<UsuarioResponseDTO> usuariosDTO = new ArrayList<>();
		for (Usuario usuario : repository.findAll()) {
			usuariosDTO.add(new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail()));
		}
		return usuariosDTO;
	}

	@Transactional
	public UsuarioResponseDTO inserir(UsuarioRequestDTO usuarioRequestDTO) {
		var user = repository.findByEmail(usuarioRequestDTO.getEmail());
		if (user.isPresent()) {
			throw new UsuarioException("Email já está cadastrado");
		}
		usuarioRequestDTO.setSenha(encoder.encode(usuarioRequestDTO.getSenha()));

		Usuario usuario = new Usuario();
		usuario.setNome(usuarioRequestDTO.getNome());
		usuario.setEmail(usuarioRequestDTO.getEmail());
		usuario.setSenha(usuarioRequestDTO.getSenha());
		usuario = repository.save(usuario);

		for (UsuarioPerfil up : usuarioRequestDTO.getUsuarioPerfis()) {
			up.setUsuario(usuario);
			up.setPerfil(perfilService.buscar(up.getPerfil().getId()));
			up.setDataCriacao(LocalDate.now());
		}
		usuarioPerfilRepository.saveAll(usuarioRequestDTO.getUsuarioPerfis());

		//mailConfig.enviarEmail(usuario.getEmail(), "Confirmação de Cadastro!", usuario.toString());

		return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
	}

}
