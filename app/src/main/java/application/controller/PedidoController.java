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

import application.model.Pedido;
import application.repository.ClienteRepository;
import application.repository.MotoboyRepository;
import application.repository.PedidoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private MotoboyRepository motoboyRepo;

    @GetMapping
    public Iterable<Pedido> getAll() {
        return pedidoRepo.findAll();
    }

    @GetMapping("/{id}")
    public Pedido getOne(@PathVariable long id) {
        Optional<Pedido> result = pedidoRepo.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Pedido Não Encontrado"
            );
        }
        return result.get();
    }

    @PostMapping
    public Pedido post(@RequestBody Pedido pedido) {
        if (!clienteRepo.existsById(pedido.getCliente().getId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Cliente vinculado não encontrado"
            );
        }
        if (!motoboyRepo.existsById(pedido.getMotoboy().getId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Motoboy vinculado não encontrado"
            );
        }
        return pedidoRepo.save(pedido);
    }

    @PutMapping("/{id}")
    public Pedido put(@RequestBody Pedido pedido, @PathVariable long id) {
        Optional<Pedido> result = pedidoRepo.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Pedido não encontrado"
            );
        }
        if (!clienteRepo.existsById(pedido.getCliente().getId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Cliente vinculado não encontrado"
            );
        }
        if (!motoboyRepo.existsById(pedido.getMotoboy().getId())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Motoboy vinculado não encontrado"
            );
        }
        Pedido existingPedido = result.get();
        existingPedido.setValor(pedido.getValor());
        existingPedido.setFormaPagamento(pedido.getFormaPagamento());
        existingPedido.setCliente(pedido.getCliente());
        existingPedido.setMotoboy(pedido.getMotoboy());

        return pedidoRepo.save(existingPedido);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        if (pedidoRepo.existsById(id)) {
            pedidoRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Pedido não encontrado"
            );
        }
    }
}
