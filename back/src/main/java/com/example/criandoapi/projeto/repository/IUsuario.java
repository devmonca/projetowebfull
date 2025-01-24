package com.example.criandoapi.projeto.repository;

import com.example.criandoapi.projeto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuario extends JpaRepository<Usuario, Integer> {
    Integer id(Integer id);
    Usuario findByEmail(String email);
}
