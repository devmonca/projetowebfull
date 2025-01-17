package com.example.criandoapi.projeto.service;

import com.example.criandoapi.projeto.model.Usuario;
import com.example.criandoapi.projeto.repository.IUsuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private IUsuario repository;

    public UsuarioService(IUsuario repository) {
        this.repository = repository;
    }

    public List<Usuario> listarUsuario(){
        List<Usuario> lista = repository.findAll();
        return lista;
    }

    public Usuario criarUsuario(Usuario usuario){
        Usuario usuarioNovo = repository.save(usuario);
        return usuarioNovo;
    }

    public Usuario editarUsuario(Usuario usuario){
        Usuario usuarioAtualizado = repository.save(usuario);
        return usuarioAtualizado;
    }

    public Boolean deletarUSuario(Integer id){
        repository.deleteById(id);
        return true;
    }


}
