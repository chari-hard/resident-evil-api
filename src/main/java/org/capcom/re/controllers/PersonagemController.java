package org.capcom.re.controllers;

import org.capcom.re.entities.Personagem;
import org.capcom.re.repositories.PersonagemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/personagens")
public class PersonagemController {
    private final PersonagemRepository repository;

    public PersonagemController(PersonagemRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Personagem> listar(){
        return repository.findAll();
    }

    @PostMapping
    public void cadastrar(@RequestBody Personagem p){
        repository.save(p);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void Editar(@PathVariable Integer id, @RequestBody Personagem p){
        Personagem personagem = repository.getReferenceById(id);
        personagem.setNome(p.getNome());
        personagem.setImagem(p.getImagem());
        personagem.setCategoria(p.getCategoria());
        personagem.setSituacao(p.getSituacao());
        repository.save(personagem);
    }
}
