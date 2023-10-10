package no.hvl.dat250.rest.todos;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
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
  public Todo createTodo(@RequestBody Todo newTodo) {
    if (newTodo.getId() == null) {
      newTodo.setId((long) (todoList.size() + 1));
    }
    todoList.add(newTodo);
    return newTodo;
  }

  @GetMapping("/todos")
  public List<Todo> getTodos() {
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
    todoList = getTodos();
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
    todoList = getTodos();
    for (Todo todo : todoList) {
      if (todo.getId().equals(id)) {
        todoList.remove(todo);
        return todoList;
      }
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id));
  }
}