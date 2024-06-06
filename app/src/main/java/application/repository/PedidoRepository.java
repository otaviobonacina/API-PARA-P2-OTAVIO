package application.repository;

import org.springframework.data.repository.CrudRepository;

import application.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {
    
}
