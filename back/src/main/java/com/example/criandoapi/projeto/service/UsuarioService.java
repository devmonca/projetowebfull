package com.example.criandoapi.projeto.service;

import com.example.criandoapi.projeto.dto.UsuarioDTO;
import com.example.criandoapi.projeto.model.Usuario;
import com.example.criandoapi.projeto.repository.IUsuario;
import com.example.criandoapi.projeto.security.Token;
import com.example.criandoapi.projeto.security.TokenUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final IUsuario repository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    public UsuarioService(IUsuario repository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.repository = repository;
    }

    public List<Usuario> listarUsuario(){
        logger.info("Usuario: "+ getLogado().toUpperCase()+ " listando usuários");
        return repository.findAll();
    }

    public Usuario criarUsuario(Usuario usuario){
        String senhaCodificada = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCodificada); // Codifique a senha antes de salvar
        logger.info("Usuario: "+ getLogado().toUpperCase()+ " criando usuário " + usuario.getNome());
        return repository.save(usuario);
    }

    public Usuario editarUsuario(Usuario usuario){
        Usuario usuarioExistente = repository.findById(usuario.getId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (!usuario.getSenha().equals(usuarioExistente.getSenha())) {
            String senhaCodificada = this.passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCodificada);
        }
        logger.info("Usuario: "+ getLogado().toUpperCase()+ " editando usuário: "+usuario.getNome());
        return repository.save(usuario);
    }

    public Boolean deletarUSuario(Integer id){
        repository.deleteById(id);
        logger.info("Usuario: "+ getLogado()+ "Excluindo usuário id: " + id);
        return true;
    }

    public Token gerarToken(@Valid UsuarioDTO usuario) {
        Usuario user = repository.findByNomeOrEmail(usuario.getNome(), usuario.getEmail());
        if(user != null){
            boolean valid = passwordEncoder.matches(usuario.getSenha(), user.getSenha());
            if(valid){
                return new Token(TokenUtil.createToken(user));
            }
        }
        return null;
    }

    private String getLogado(){
        Authentication userLogado = SecurityContextHolder.getContext().getAuthentication();
        if(!(userLogado instanceof AnonymousAuthenticationToken)){
            return userLogado.getName();
        }
        return "Null";
    }
}
