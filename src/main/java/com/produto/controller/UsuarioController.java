package com.produto.controller;

import com.produto.config.ProdutoException;
import com.produto.model.DTO.LoginRequestDTO;
import com.produto.model.DTO.UsuarioDTO;
import com.produto.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar Usuario")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
        } catch (ProdutoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login Usuario")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            UsuarioDTO usuarioLogado = usuarioService.login(loginRequest);
            return ResponseEntity.ok(usuarioLogado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erro no login: " + e.getMessage());
        }
    }
}
