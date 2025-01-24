package com.example.criandoapi.projeto.model;

import jakarta.persistence.*;

@Entity
@Table(name= "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome",length = 200, nullable = false)
    private String nome;

    @Column(name = "sobrenome",length = 200, nullable = false)
    private String sobrenome;

    @Column(name = "idade", nullable = false)
    private Integer idade;

    @Column(name = "email",length = 200, nullable = false)
    private String email;

    @Column(name = "senha",length = 200, nullable = false)
    private String senha;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
