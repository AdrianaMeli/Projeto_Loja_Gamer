package com.generation.loja_gamer.repository;

import com.generation.loja_gamer.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutosRepository extends JpaRepository<Produto, Long> {

    public List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

    public List <Produto> findByPrecoGreaterThanOrderByPreco(BigDecimal preco);


    public List <Produto> findByPrecoLessThanOrderByPrecoDesc(BigDecimal preco);

}


