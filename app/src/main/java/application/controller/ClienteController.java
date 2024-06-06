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

import application.model.Cliente;
import application.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepo;

    @GetMapping
    public Iterable<Cliente> getAll() {
        return clienteRepo.findAll();
    }

    @GetMapping("/{id}")
    public Cliente getOne(@PathVariable long id) {
        Optional<Cliente> result = clienteRepo.findById(id);
        if(result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cliente Não Encontrado"
            );
        }
        return result.get();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        if(clienteRepo.existsById(id)) {
            clienteRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cliente Não Encontrado"
            );
        }
    }

    @PostMapping
    public Cliente post(@RequestBody Cliente cliente) {
        return clienteRepo.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente put(@RequestBody Cliente cliente, @PathVariable long id) {
        Optional<Cliente> result = clienteRepo.findById(id);
        if(result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cliente Não Encontrado"
            );
        }
        Cliente existingCliente = result.get();
        existingCliente.setNome(cliente.getNome());
        existingCliente.setEndereco(cliente.getEndereco());

        return clienteRepo.save(existingCliente);
    }
}