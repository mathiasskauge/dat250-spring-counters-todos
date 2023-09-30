package no.hvl.dat250.rest.todos.controller;
import no.hvl.dat250.rest.todos.model.Todo;
import no.hvl.dat250.rest.todos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest-Endpoint for todos.
 */

@RestController
public class TodoController {

  public static final String TODO_WITH_THE_ID_X_NOT_FOUND = "Todo with the id %s not found!";

  @Autowired
  private TodoRepository todoRepository;

  // Create a new Todo
  @PostMapping
  public Todo createTodo(@RequestBody Todo todo) {
    return todoRepository.save(todo);
  }

  // Retrieve all Todos
  @GetMapping
  public List<Todo> getAllTodos() {
    return todoRepository.findAll();
  }

  // Retrieve a specific Todo by ID
  @GetMapping("/{id}")
  public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
    Todo todo = todoRepository.findById(id).orElse(null);
    if (todo != null) {
      return ResponseEntity.ok(todo);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // Update an existing Todo
  @PutMapping("/{id}")
  public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
    Todo todo = todoRepository.findById(id).orElse(null);
    if (todo != null) {
      todo.setSummary(updatedTodo.getSummary());
      todo.setDescription(updatedTodo.getDescription());
      return ResponseEntity.ok(todoRepository.save(todo));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // Delete a Todo by ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
    Todo todo = todoRepository.findById(id).orElse(null);
    if (todo != null) {
      todoRepository.delete(todo);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
