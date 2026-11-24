package org.capcom.re.controllers;

import org.capcom.re.entities.Virus;
import org.capcom.re.repositories.VirusRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/virus")
@CrossOrigin(origins = "*")
public class VirusController {
    private final VirusRepository repository;

    public VirusController(VirusRepository repository){
        this.repository = repository;
    }


    @GetMapping
    public List<Virus> listar(){
        return repository.findAll();
    }

    @PostMapping
    public void cadastrar(@RequestBody Virus v){
        repository.save(v);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void Editar(@PathVariable Integer id, @RequestBody Virus v){
        Virus virus = repository.getReferenceById(id);
        virus.setNome(v.getNome());
        virus.setImagem(v.getImagem());
        virus.setAnoDeCriacao(v.getAnoDeCriacao());
        repository.save(virus);
    }
}
