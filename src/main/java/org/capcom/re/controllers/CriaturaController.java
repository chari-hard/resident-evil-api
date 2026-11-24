package org.capcom.re.controllers;

import org.capcom.re.entities.Criatura;
import org.capcom.re.entities.Incidente;
import org.capcom.re.entities.Sobrevivente;
import org.capcom.re.repositories.CriaturaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/criaturas")
@CrossOrigin(origins = "*")
public class CriaturaController {
    private final CriaturaRepository repository;

    public CriaturaController(CriaturaRepository repository){
        this.repository = repository;
    }


    @GetMapping
    public List<Criatura> listar(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Criatura buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public void cadastrar(@RequestBody Criatura c){
        repository.save(c);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void Editar(@PathVariable Integer id, @RequestBody Criatura c){
        Criatura criatura = repository.getReferenceById(id);
        criatura.setNome(c.getNome());
        criatura.setImagem(c.getImagem());
        criatura.setVirus(c.getVirus());
        repository.save(criatura);
    }
}
