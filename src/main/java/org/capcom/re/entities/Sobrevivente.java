package org.capcom.re.entities;

import jakarta.persistence.*;

@Entity
public class Sobrevivente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    @Column(columnDefinition = "TEXT")
    private String imagem;
    private String incidente;

    public Sobrevivente(Integer id, String nome, String imagem, String incidente) {
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
        this.incidente = incidente;
    }

    public Sobrevivente(String nome, String imagem, String incidente) {
        this.nome = nome;
        this.imagem = imagem;
        this.incidente = incidente;
    }

    public Sobrevivente() {
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

    public String getIncidente() {
        return incidente;
    }

    public void setIncidente(String incidente) {
        this.incidente = incidente;
    }
}
