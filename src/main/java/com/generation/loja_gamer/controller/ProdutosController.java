package com.generation.loja_gamer.controller;



import com.generation.loja_gamer.model.Produto;

import com.generation.loja_gamer.repository.CategoriaRepository;
import com.generation.loja_gamer.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

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
    @GetMapping("{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id) {
        return produtosRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
        return ResponseEntity.ok( produtosRepository.findAllByNomeContainingIgnoreCase(nome));

    }

    @PostMapping
    public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto){
        return categoriaRepository.findById(produto.getCategoria().getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.save(produto)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping
    public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto) {

        if (produtosRepository.existsById(produto.getId())){

            return categoriaRepository.findById(produto.getCategoria().getId())
                    .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(produtosRepository.save(produto)))
                    .orElse(ResponseEntity.badRequest().build());
        }

        return ResponseEntity.notFound().build();

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



