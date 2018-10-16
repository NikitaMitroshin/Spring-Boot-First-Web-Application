package com.in28minutes.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.springboot.web.model.Todo;
import com.in28minutes.springboot.web.service.TodoService;

@RestController
public class TodoRestController {
    @Autowired
    private TodoService service;

    @RequestMapping(value = "/rest/todos", method = RequestMethod.GET)
    public List<Todo> listAllTodos() {
        List<Todo> users = service.retrieveTodos("in28Minutes");
        return users;
    }

    @RequestMapping(value = "/rest/todos/{id}", method = RequestMethod.GET)
    public Todo retrieveTodo(@PathVariable("id") int id) {
        return service.retrieveTodo(id);
    }

//    @RequestMapping(value = "/rest/todos/{id}/{desc}", method = RequestMethod.GET)
//    public Map<String, String> retrieveTodo(@PathVariable("id") int id, @PathVariable("desc") String desc) {
//        Map<String, String> resultMap = new HashMap<>();
//        Todo todo = service.retrieveTodo(id);
//        if (todo != null) {
//            resultMap.put("id", String.valueOf(id));
//            resultMap.put("oldDescription", todo.getDesc());
//            todo.setDesc(desc);
//            service.updateTodo(todo);
//            resultMap.put("newDescription", desc);
//            return resultMap;
//
//        }
//        resultMap.put("error", "cant find todo with id = " + id);
//        return resultMap;
//    }

    @RequestMapping(value = "/rest/todos/{id}/{desc}", method = RequestMethod.GET)
    public ControllerResponse retrieveTodo(@PathVariable("id") int id, @PathVariable("desc") String desc) {
        Todo todo = service.retrieveTodo(id);
        if (todo != null) {
            String oldDesc = todo.getDesc();
            todo.setDesc(desc);
            service.updateTodo(todo);
            return new ControllerResponse(id, oldDesc, desc);
        }
        return new ControllerResponse("can't get todo with id = " + id);
    }

    public static class ControllerResponse {
        private Integer id;
        private String oldDescription;
        private String newDescription;
        private String error;

        public ControllerResponse(String error) {
            this.error = error;
        }

        public ControllerResponse(int id, String oldDescription, String newDescription) {
            this.id = id;
            this.oldDescription = oldDescription;
            this.newDescription = newDescription;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOldDescription() {
            return oldDescription;
        }

        public void setOldDescription(String oldDescription) {
            this.oldDescription = oldDescription;
        }

        public String getNewDescription() {
            return newDescription;
        }

        public void setNewDescription(String newDescription) {
            this.newDescription = newDescription;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}