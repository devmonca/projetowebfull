package com.example.criandoapi.projeto.dto;

import com.example.criandoapi.projeto.model.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;


@Getter
@Setter
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;

    public UsuarioDTO(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}
