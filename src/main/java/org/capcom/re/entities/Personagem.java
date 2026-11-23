package org.capcom.re.entities;

import jakarta.persistence.*;

@Entity
public class Personagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String imagem;
    private String situacao;

    public Personagem(Integer id, String nome, String imagem, String situacao) {
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
        this.situacao = situacao;
    }

    public Personagem(String nome, String imagem, String situacao) {
        this.nome = nome;
        this.imagem = imagem;
        this.situacao = situacao;
    }

    public Personagem() {
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

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}


