package com.generation.loja_gamer.repository;


import com.generation.loja_gamer.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        public Optional <Usuario> findByUsuario(String usuario);



}
