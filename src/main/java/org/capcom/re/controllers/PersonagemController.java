package org.capcom.re.controllers;

import org.capcom.re.entities.Personagem;
import org.capcom.re.repositories.PersonagemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/personagens")
@CrossOrigin(origins = "*")
public class PersonagemController {
    private final PersonagemRepository repository;

    public PersonagemController(PersonagemRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Personagem> listar(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Personagem buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
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
        personagem.setSituacao(p.getSituacao());
        repository.save(personagem);
    }
}
