package application.repository;

import org.springframework.data.repository.CrudRepository;

import application.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
   
}