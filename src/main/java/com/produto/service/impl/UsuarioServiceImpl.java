package com.produto.service.impl;

import com.produto.config.ProdutoException;
import com.produto.model.DTO.LoginRequestDTO;
import com.produto.model.DTO.UsuarioDTO;
import com.produto.model.Usuario;
import com.produto.repository.UsuarioRepository;
import com.produto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                throw new ProdutoException("Email já cadastrado.");
            }

            Usuario usuario = new Usuario();
            usuario.setNome(usuarioDTO.getNome());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            usuario = usuarioRepository.save(usuario);
            return convertToDTO(usuario);
        } catch (ProdutoException e) {
            throw new ProdutoException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @Override
    public UsuarioDTO login(LoginRequestDTO loginRequest) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                if (passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())) {
                    return convertToDTO(usuario);
                } else {
                    throw new RuntimeException("Senha incorreta");
                }
            } else {
                throw new RuntimeException("Usuário não encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        return usuarioDTO;
    }
}
