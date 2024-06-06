package application.repository;

import org.springframework.data.repository.CrudRepository;

import application.model.Motoboy;

public interface MotoboyRepository extends CrudRepository<Motoboy, Long> {
    
}
