package com.produto.service;

import com.produto.model.DTO.LoginRequestDTO;
import com.produto.model.DTO.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO);
    UsuarioDTO login(LoginRequestDTO loginRequest);
}
