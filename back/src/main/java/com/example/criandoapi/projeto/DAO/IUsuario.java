package com.example.criandoapi.projeto.DAO;

import com.example.criandoapi.projeto.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuario extends CrudRepository<Usuario, Integer> {
    Integer id(Integer id);
}
