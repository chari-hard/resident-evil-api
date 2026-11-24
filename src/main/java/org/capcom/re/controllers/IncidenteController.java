package org.capcom.re.controllers;

import org.capcom.re.entities.Incidente;
import org.capcom.re.repositories.IncidenteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/incidentes")
@CrossOrigin(origins = "*")
public class IncidenteController {
    private final IncidenteRepository repository;

    public IncidenteController(IncidenteRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Incidente> listar(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Incidente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public void cadastrar(@RequestBody Incidente i){
        repository.save(i);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void Editar(@PathVariable Integer id, @RequestBody Incidente i){
        Incidente incidente = repository.getReferenceById(id);
        incidente.setNome(i.getNome());
        incidente.setImagem(i.getImagem());
        incidente.setAno(i.getAno());
        repository.save(incidente);
    }
}
