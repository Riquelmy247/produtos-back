package com.produto.model.filter;

import com.produto.model.Produto;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoFilter {

    private String nome;
    private String categoria;
    private String descricao;
    private Integer quantidade;
    private Double precoMin;
    private Double precoMax;
    private boolean ordenarPrecoAsc = true;
    private boolean ordenarQuantidadeAsc = true;

    public static Specification<Produto> nomeContains(String nome) {
        return (root, query, builder) -> nome != null ? builder.like(builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%") : null;
    }

    public static Specification<Produto> categoriaEquals(String categoria) {
        return (root, query, builder) -> categoria != null ? builder.equal(root.get("categoria"), categoria) : null;
    }

    public static Specification<Produto> descricaoContains(String descricao) {
        return (root, query, builder) -> descricao != null ? builder.like(builder.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%") : null;
    }

    public static Specification<Produto> quantidadeEquals(Integer quantidade) {
        return (root, query, builder) -> quantidade != null ? builder.equal(root.get("quantidade"), quantidade) : null;
    }

    public static Specification<Produto> precoGreaterThanOrEqual(Double precoMin) {
        return (root, query, builder) -> precoMin != null ? builder.greaterThanOrEqualTo(root.get("preco"), precoMin) : null;
    }

    public static Specification<Produto> precoLessThanOrEqual(Double precoMax) {
        return (root, query, builder) -> precoMax != null ? builder.lessThanOrEqualTo(root.get("preco"), precoMax) : null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoMin() {
        return precoMin;
    }

    public void setPrecoMin(Double precoMin) {
        this.precoMin = precoMin;
    }

    public Double getPrecoMax() {
        return precoMax;
    }

    public void setPrecoMax(Double precoMax) {
        this.precoMax = precoMax;
    }

    public boolean isOrdenarPrecoAsc() {
        return ordenarPrecoAsc;
    }

    public void setOrdenarPrecoAsc(boolean ordenarPrecoAsc) {
        this.ordenarPrecoAsc = ordenarPrecoAsc;
    }

    public boolean isOrdenarQuantidadeAsc() {
        return ordenarQuantidadeAsc;
    }

    public void setOrdenarQuantidadeAsc(boolean ordenarQuantidadeAsc) {
        this.ordenarQuantidadeAsc = ordenarQuantidadeAsc;
    }
}
