package com.produto.controller;

import com.produto.model.DTO.ProdutoDTO;
import com.produto.model.filter.ProdutoFilter;
import com.produto.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @Operation(summary = "Cadastrar Produto")
    public ResponseEntity<?> cadastrarProduto(@RequestBody ProdutoDTO produtoDTO) {
        try {
            ProdutoDTO produtoCadastrado = produtoService.cadastrarProduto(produtoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoCadastrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar Produto")
    public ResponseEntity<?> editarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        try {
            ProdutoDTO produtoEditado = produtoService.editarProduto(id, produtoDTO);
            return ResponseEntity.ok(produtoEditado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao editar produto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir Produto")
    public ResponseEntity<?> excluirProduto(@PathVariable Long id) {
        try {
            produtoService.excluirProduto(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir produto: " + e.getMessage());
        }
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar Produto por Nome")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        try {
            List<ProdutoDTO> produtos = produtoService.buscarPorNome(nome);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar produtos por nome: " + e.getMessage());
        }
    }

    @GetMapping("/filtro")
    @Operation(summary = "Filtro Produto")
    public ResponseEntity<?> buscarComFiltros(ProdutoFilter filter) {
        try {
            List<ProdutoDTO> produtos = produtoService.buscarComFiltros(filter);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar produtos com filtros: " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Listar Produtos")
    public ResponseEntity<?> listarProdutos() {
        try {
            List<ProdutoDTO> produtos = produtoService.listarProdutos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar produtos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Produto por ID")
    public ResponseEntity<?> buscarProdutoPorId(@PathVariable Long id) {
        try {
            ProdutoDTO produto = produtoService.buscarProdutoPorId(id);
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
}
