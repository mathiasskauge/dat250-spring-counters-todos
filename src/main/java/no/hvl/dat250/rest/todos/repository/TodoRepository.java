package no.hvl.dat250.rest.todos.repository;

import no.hvl.dat250.rest.todos.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
