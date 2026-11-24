package org.capcom.re.controllers;

import org.capcom.re.entities.Sobrevivente;
import org.capcom.re.repositories.SobreviventeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sobreviventes")
@CrossOrigin(origins = "*")
public class SobreviventeController {
    private final SobreviventeRepository repository;

    public SobreviventeController(SobreviventeRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Sobrevivente> listar(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Sobrevivente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public void cadastrar(@RequestBody Sobrevivente s){
        repository.save(s);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void Editar(@PathVariable Integer id, @RequestBody Sobrevivente s){
        Sobrevivente sobrevivente = repository.getReferenceById(id);
        sobrevivente.setNome(s.getNome());
        sobrevivente.setImagem(s.getImagem());
        sobrevivente.setIncidente(s.getIncidente());
        repository.save(sobrevivente);
    }
}
