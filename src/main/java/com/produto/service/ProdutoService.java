package com.produto.service;

import com.produto.model.DTO.ProdutoDTO;
import com.produto.model.filter.ProdutoFilter;

import java.util.List;

public interface ProdutoService {
    ProdutoDTO cadastrarProduto(ProdutoDTO produtoDTO);
    ProdutoDTO editarProduto(Long id, ProdutoDTO produtoDTO);
    void excluirProduto(Long id);
    List<ProdutoDTO> buscarPorNome(String nome);
    List<ProdutoDTO> buscarComFiltros(ProdutoFilter filter);
    List<ProdutoDTO> listarProdutos();
    ProdutoDTO buscarProdutoPorId(Long id);
}