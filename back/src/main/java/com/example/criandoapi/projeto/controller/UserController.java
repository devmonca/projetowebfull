package com.example.criandoapi.projeto.controller;

import com.example.criandoapi.projeto.repository.IUsuario;
import com.example.criandoapi.projeto.model.Usuario;
import com.example.criandoapi.projeto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UserController {

    private UsuarioService usuarioService;
    public UserController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios() {
        return ResponseEntity.status(200).body(usuarioService.listarUsuario());
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.status(201).body(usuarioService.criarUsuario(usuario));
    }

    @PutMapping
    public ResponseEntity<Usuario> editarUsuario(@RequestBody Usuario usuario){
        Usuario usuarioEditado = usuarioService.editarUsuario(usuario);
        return ResponseEntity.status(200).body(usuarioService.editarUsuario(usuarioEditado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Integer id){
        usuarioService.deletarUSuario(id);
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> validarSenha(@RequestBody Usuario usuario){
        Boolean isValid = usuarioService.validarSenha(usuario.getEmail(),usuario.getSenha());
        if(!isValid){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
