package com.example.criandoapi.projeto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

@Getter
@Setter

@Entity
@Table(name= "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "nome",length = 200, nullable = false)
    private String nome;

    @NotBlank(message = "O sobrenome é obrigatório")
    @Column(name = "sobrenome",length = 200, nullable = false)
    private String sobrenome;

    @NotNull(message = "A idade é obrigatório")
    @Column(name = "idade", nullable = false)
    private Integer idade;

    @Email(message = "Insira um email válido")
    @Column(name = "email", columnDefinition = "TEXT", nullable = false)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter ao menos 8 caracteres")
    @Column(name = "senha",length = 200, nullable = false)
    private String senha;

}
