package org.capcom.re.entities;

import jakarta.persistence.*;

@Entity
public class Criatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Column(columnDefinition = "TEXT")
    private String imagem;
    private String virus;

    public Criatura(Integer id, String nome, String imagem, String virus) {
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
        this.virus = virus;
    }

    public Criatura(String nome, String imagem, String virus) {
        this.nome = nome;
        this.imagem = imagem;
        this.virus = virus;
    }

    public Criatura() {
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

    public String getVirus() {
        return virus;
    }

    public void setVirus(String virus) {
        this.virus = virus;
    }
}
