package org.capcom.re.entities;

import jakarta.persistence.*;

@Entity
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Column(columnDefinition = "TEXT")
    private String imagem;
    private String ano;

    public Incidente(Integer id, String nome, String imagem, String ano) {
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
        this.ano = ano;
    }

    public Incidente(String nome, String imagem, String ano) {
        this.nome = nome;
        this.imagem = imagem;
        this.ano = ano;
    }

    public Incidente() {
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

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}
