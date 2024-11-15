package com.produto.repository;

import com.produto.model.Produto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContaining(String nome);
    List<Produto> findAll(Specification<Produto> spec, Sort orders);

    @Query(value = "SELECT MAX(id) + 1 FROM trabalho.produto", nativeQuery = true)
    Long findNextId();
}