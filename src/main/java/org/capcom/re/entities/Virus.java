package org.capcom.re.entities;

import jakarta.persistence.*;

@Entity
public class Virus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Column(columnDefinition = "TEXT")
    private String imagem;
    private String anoDeCriacao;

    public Virus(Integer id, String nome, String imagem, String anoDeCriacao) {
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
        this.anoDeCriacao = anoDeCriacao;
    }

    public Virus(String nome, String imagem, String anoDeCriacao) {
        this.nome = nome;
        this.imagem = imagem;
        this.anoDeCriacao = anoDeCriacao;
    }

    public Virus() {
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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getAnoDeCriacao() {
        return anoDeCriacao;
    }

    public void setAnoDeCriacao(String anoDeCriacao) {
        this.anoDeCriacao = anoDeCriacao;
    }
}
