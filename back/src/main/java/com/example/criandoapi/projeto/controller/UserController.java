package com.example.criandoapi.projeto.controller;

import com.example.criandoapi.projeto.dto.UsuarioDTO;
import com.example.criandoapi.projeto.repository.IUsuario;
import com.example.criandoapi.projeto.model.Usuario;
import com.example.criandoapi.projeto.security.Token;
import com.example.criandoapi.projeto.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario){
        return ResponseEntity.status(201).body(usuarioService.criarUsuario(usuario));
    }

    @PutMapping
    public ResponseEntity<Usuario> editarUsuario(@Valid @RequestBody Usuario usuario){
        Usuario usuarioEditado = usuarioService.editarUsuario(usuario);
        return ResponseEntity.status(200).body(usuarioService.editarUsuario(usuarioEditado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Integer id){
        usuarioService.deletarUSuario(id);
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Token> logar(@Valid @RequestBody UsuarioDTO usuario){
        Token token = usuarioService.gerarToken(usuario);
        if(token !=null){
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(403).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
