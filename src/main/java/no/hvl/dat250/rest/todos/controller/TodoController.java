package no.hvl.dat250.rest.todos.controller;
import no.hvl.dat250.rest.todos.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest-Endpoint for todos.
 */

@RestController
public class TodoController {

  private List<Todo> todoList = new ArrayList<>();


  public static final String TODO_WITH_THE_ID_X_NOT_FOUND = "Todo with the id %s not found!";

  // Create a new Todo
  @PostMapping("/todos")
  public Todo createTodo(@RequestBody Todo todo) {
    if (todo.getId() == null) {
      todo.setId((long) (todoList.size() + 1));
    }
    todoList.add(todo);
    return todo;
  }

  // Retrieve all Todos
  @GetMapping
  public List<Todo> getAllTodos() {
    return todoList;
  }

  // Retrieve a specific Todo by ID
  @GetMapping("/todos/{id}")
  public Object getTodoById(@PathVariable Long id) {
    for (Todo todo : todoList) {
      if (todo.getId().equals(id)) {
        return todo;
      }
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id));
  }

  // Update an existing Todo
  @PutMapping("/todos/{id}")
  public Object updateTodoById(@PathVariable Long id, @RequestBody Todo newTodo) {
    todoList = getAllTodos();
    for (Todo todo : todoList) {
      if (todo.getId().equals(id)) {
        todo.setSummary(newTodo.getSummary());
        todo.setDescription(newTodo.getDescription());
        return todo;
      }
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id));
  }

  // Delete a Todo by ID
  @DeleteMapping("/todos/{id}")
  public Object deleteTodoById(@PathVariable Long id) {
    todoList = getAllTodos();
    for (Todo todo : todoList) {
      if (todo.getId().equals(id)) {
        todoList.remove(todo);
        return todoList;
      }
    }
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id));
  }

}

