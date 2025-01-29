package com.example.criandoapi.projeto.service;

import com.example.criandoapi.projeto.dto.UsuarioDTO;
import com.example.criandoapi.projeto.model.Usuario;
import com.example.criandoapi.projeto.repository.IUsuario;
import com.example.criandoapi.projeto.security.Token;
import com.example.criandoapi.projeto.security.TokenUtil;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private IUsuario repository;
    private PasswordEncoder passwordEncoder;

    public UsuarioService(IUsuario repository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.repository = repository;
    }

    public List<Usuario> listarUsuario(){
        List<Usuario> lista = repository.findAll();
        return lista;
    }

    public Usuario criarUsuario(Usuario usuario){
        String senhaCodificada = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCodificada); // Codifique a senha antes de salvar
        return repository.save(usuario);
    }

    public Usuario editarUsuario(Usuario usuario){
        Usuario usuarioExistente = repository.findById(usuario.getId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (!usuario.getSenha().equals(usuarioExistente.getSenha())) {
            String senhaCodificada = this.passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCodificada);
        }
        return repository.save(usuario);
    }

    public Boolean deletarUSuario(Integer id){
        repository.deleteById(id);
        return true;
    }


    public Boolean validarSenha(String email, String senha){
        Usuario usuario = repository.findByEmail(email);

        if (usuario == null) {
            return false;
        }

        return passwordEncoder.matches(senha, usuario.getSenha());
    }

    public Token gerarToken(@Valid UsuarioDTO usuario) {
        Usuario user = repository.findByNomeOrEmail(usuario.getNome(), usuario.getEmail());
        if(user != null){
            Boolean valid = passwordEncoder.matches(usuario.getSenha(), user.getSenha());
            if(valid){
                return new Token(TokenUtil.createToken(user));
            }
        }
        return null;
    }
}
