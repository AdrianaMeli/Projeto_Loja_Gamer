package com.generation.loja_gamer.controller;


import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import com.generation.loja_gamer.model.Produto;
import com.generation.loja_gamer.repository.CategoriaRepository;
import com.generation.loja_gamer.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {

    @Autowired
    private ProdutosRepository produtosRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;



    @GetMapping
    public ResponseEntity<List<Produto>> getAll() {
        return (ResponseEntity.ok(produtosRepository.findAll()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id){
        return produtosRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")

        public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
            return ResponseEntity.ok(produtosRepository.findAllByNomeContainingIgnoreCase(nome));
    }

    @PostMapping
    public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto){
        if (categoriaRepository.existsById(produto.getCategoria().getId()))
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(produtosRepository.save(produto));

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria nao existe!", null);
    }

    @PutMapping public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto) {

        if (produtosRepository.existsById(produto.getId())){

            if (categoriaRepository.existsById(produto.getCategoria().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(produtosRepository.save(produto));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria nao existe!", null);

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable Long id) {

        return produtosRepository.findById(id)
                .map(resposta -> {
                    produtosRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/preco_maior/{preco}")
    public ResponseEntity<List<Produto>> getPrecoMaiorQue(@PathVariable BigDecimal preco){
        return ResponseEntity.ok(produtosRepository.findByPrecoGreaterThanOrderByPreco(preco));
    }



    @GetMapping("/preco_menor/{preco}")
    public ResponseEntity<List<Produto>> getPrecoMenorQue(@PathVariable BigDecimal preco){
        return ResponseEntity.ok(produtosRepository.findByPrecoLessThanOrderByPrecoDesc(preco));
    }

}
