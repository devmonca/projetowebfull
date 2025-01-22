package com.example.criandoapi.projeto.service;

import com.example.criandoapi.projeto.model.Usuario;
import com.example.criandoapi.projeto.repository.IUsuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private IUsuario repository;
    private PasswordEncoder passwordEncoder;

    public UsuarioService(IUsuario repository) {
        this.repository = repository;
        this.passwordEncoder= new BCryptPasswordEncoder();
    }

    public List<Usuario> listarUsuario(){
        List<Usuario> lista = repository.findAll();
        return lista;
    }

    public Usuario criarUsuario(Usuario usuario){
        String encoder = this.passwordEncoder.encode(usuario.getEmail());
        usuario.setEmail(encoder);
        Usuario usuarioNovo = repository.save(usuario);
        return usuarioNovo;
    }

    public Usuario editarUsuario(Usuario usuario){
        String encoder = this.passwordEncoder.encode(usuario.getEmail());
        usuario.setEmail(encoder);
        Usuario usuarioAtualizado = repository.save(usuario);
        return usuarioAtualizado;
    }

    public Boolean deletarUSuario(Integer id){
        repository.deleteById(id);
        return true;
    }


    public Boolean validarEmail(Usuario usuario) {
        String email = repository.getById(usuario.getId()).getEmail();
        Boolean valid = passwordEncoder.matches(usuario.getEmail(),email);
        Boolean valid2 = usuario.getEmail()== email ? true : false;
        return valid2;
    }
}
