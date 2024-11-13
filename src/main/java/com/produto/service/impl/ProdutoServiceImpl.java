package com.produto.service.impl;

import com.produto.model.DTO.ProdutoDTO;
import com.produto.model.Produto;
import com.produto.model.filter.ProdutoFilter;
import com.produto.repository.ProdutoRepository;
import com.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public ProdutoDTO cadastrarProduto(ProdutoDTO produtoDTO) {
        try {
            Produto produto = new Produto();
            produto.setNome(produtoDTO.getNome());
            produto.setPreco(produtoDTO.getPreco());
            produto.setDescricao(produtoDTO.getDescricao());
            produto.setCategoria(produtoDTO.getCategoria());
            produto.setQuantidade(produtoDTO.getQuantidade());
            produto = produtoRepository.save(produto);
            return convertToDTO(produto);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProdutoDTO editarProduto(Long id, ProdutoDTO produtoDTO) {
        try {
            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado para o ID: " + id));
            produto.setNome(produtoDTO.getNome());
            produto.setPreco(produtoDTO.getPreco());
            produto.setDescricao(produtoDTO.getDescricao());
            produto.setCategoria(produtoDTO.getCategoria());
            produto.setQuantidade(produtoDTO.getQuantidade());
            produto = produtoRepository.save(produto);
            return convertToDTO(produto);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void excluirProduto(Long id) {
        try {
            produtoRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ProdutoDTO> buscarPorNome(String nome) {
        try {
            return produtoRepository.findByNomeContaining(nome).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

//    @Override
//    public List<ProdutoDTO> buscarComFiltros(ProdutoFilter filter) {
//        try {
//            Specification<Produto> spec = Specification.where(ProdutoFilter.nomeContains(filter.getNome()))
//                    .and(ProdutoFilter.categoriaEquals(filter.getCategoria()))
//                    .and(ProdutoFilter.descricaoContains(filter.getDescricao()))
//                    .and(ProdutoFilter.precoGreaterThanOrEqual(filter.getPrecoMin()))
//                    .and(ProdutoFilter.precoLessThanOrEqual(filter.getPrecoMax()));
//
//            List<Produto> produtos = produtoRepository.findAll(
//                    spec,
//                    filter.isOrdenarPrecoAsc() ? Sort.by("preco").ascending() : Sort.by("preco").descending()
//            );
//
//            return produtos.stream().map(this::convertToDTO).collect(Collectors.toList());
//        } catch (DataAccessException e) {
//            throw new RuntimeException("Erro ao buscar produtos com filtros: " + e.getMessage(), e);
//        }
//    }

    @Override
    public List<ProdutoDTO> buscarComFiltros(ProdutoFilter filter) {
        try {
            // Criando a Specification com os filtros
            Specification<Produto> spec = Specification.where(ProdutoFilter.nomeContains(filter.getNome()))
                    .and(ProdutoFilter.categoriaEquals(filter.getCategoria()))
                    .and(ProdutoFilter.descricaoContains(filter.getDescricao()))
                    .and(ProdutoFilter.quantidadeEquals(filter.getQuantidade()))
                    .and(ProdutoFilter.precoGreaterThanOrEqual(filter.getPrecoMin()))
                    .and(ProdutoFilter.precoLessThanOrEqual(filter.getPrecoMax()));

            // Criando a lista de orders que vai ser usada para ordenação
            List<Sort.Order> orders = new ArrayList<>();

            // Ordenação pelo preço
            if (filter.isOrdenarPrecoAsc()) {
                orders.add(Sort.Order.asc("preco")); // Preço do menor para o maior
            } else {
                orders.add(Sort.Order.desc("preco")); // Preço do maior para o menor
            }

            // Ordenação pela quantidade
            if (filter.isOrdenarQuantidadeAsc()) {
                orders.add(Sort.Order.asc("quantidade")); // Quantidade do menor para o maior
            } else {
                orders.add(Sort.Order.desc("quantidade")); // Quantidade do maior para o menor
            }

            // Criando o objeto Sort a partir da lista de Orders
            Sort sort = Sort.by(orders);

            // Realizando a consulta com a Specification e a ordenação
            List<Produto> produtos = produtoRepository.findAll(spec, sort);

            // Convertendo os produtos para DTO
            return produtos.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao buscar produtos com filtros: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ProdutoDTO> listarProdutos() {
        try {
            return produtoRepository.findAll().stream()
                    .sorted(Comparator.comparing(Produto::getNome))
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProdutoDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));
        return convertToDTO(produto);
    }

    private ProdutoDTO convertToDTO(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setDescricao(produto.getDescricao());
        produtoDTO.setCategoria(produto.getCategoria());
        produtoDTO.setQuantidade(produto.getQuantidade());
        return produtoDTO;
    }
}
