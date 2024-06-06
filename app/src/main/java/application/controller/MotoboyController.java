package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Motoboy;
import application.repository.MotoboyRepository;

@RestController
@RequestMapping("/motoboys")
public class MotoboyController {
    @Autowired
    private MotoboyRepository motoboyRepo;

    @GetMapping
    public Iterable<Motoboy> getAll() {
        return motoboyRepo.findAll();
    }

    @PostMapping
    public Motoboy post(@RequestBody Motoboy motoboy) {
        return motoboyRepo.save(motoboy);
    }

    @PutMapping("/{id}")
    public Motoboy put(@RequestBody Motoboy motoboy, @PathVariable long id) {
        Optional<Motoboy> result = motoboyRepo.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Motoboy Não Encontrado"
            );
        }
        motoboy.setId(id); // Ensure the ID is set
        return motoboyRepo.save(motoboy);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        if (motoboyRepo.existsById(id)) {
            motoboyRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Motoboy Não Encontrado"
            );
        }
    }
}