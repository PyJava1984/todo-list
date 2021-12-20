package com.smefinance.test.core.controllers;

import com.smefinance.test.core.models.*;
import com.smefinance.test.core.repository.TodoItemRepository;
import com.smefinance.test.core.repository.TodoListRepository;
import com.smefinance.test.core.security.services.UserDetailsImpl;
import com.smefinance.test.core.service.TodolistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/todolist")
public class TodoListController {
    private static final Logger logger = LoggerFactory.getLogger(TodoListController.class);
    @Autowired
    private TodolistService todolistService;

    /**
     * This method will create List in TODO List
     * @param todoListRequest
     * @param authentication
     * @return
     */
    @PostMapping("/lists")
    public ResponseEntity<TodoListCreatedResponse> create(@RequestBody TodoListRequest todoListRequest,
                                                          Authentication authentication) {
        try {
            HashMap<String, Object> response = todolistService.create(todoListRequest, authentication);
            if (response.get("data") != null) {
                TodoList todoList = (TodoList) response.get("data");
                return new ResponseEntity<>(TodoListCreatedResponse.from(todoList), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method will create Task in TODO List
     * @param id
     * @param todoItemRequest
     * @param authentication
     * @return
     */
    @PostMapping("/lists/{id}/items")
    public ResponseEntity<TodoItem> createItem(@PathVariable("id") Long id, @RequestBody TodoItemRequest todoItemRequest,
                                                              Authentication authentication) {
        try {
            HashMap<String, Object> response = todolistService.createItem(id, todoItemRequest, authentication);
            if(response.get("data") != null){
                TodoItem todoItem = (TodoItem) response.get("data");
                return new ResponseEntity<>(todoItem, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method will get all List of TODO List
     * @param authentication
     * @return
     */
    @GetMapping("/lists")
    public ResponseEntity<Iterable<TodoList>> list(Authentication authentication) {

        try {
            HashMap<String, Object> response = todolistService.list(authentication);
            if(response.get("data") != null){
                List<TodoList> allByOwner = (List<TodoList>) response.get("data");
                return new ResponseEntity<>(allByOwner, HttpStatus.FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method will get all Task of a list in TODO List
     * @param authentication
     * @return
     */
    @GetMapping("/lists/{id}")
    public ResponseEntity<TodoList> get(@PathVariable("id") Long id,
                                        Authentication authentication) {
        try {
            HashMap<String, Object> response = todolistService.get(id, authentication);
            if(response.get("data") != null){
                TodoList todoList = (TodoList) response.get("data");
                return new ResponseEntity<>(todoList, HttpStatus.FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method will get all completed Task of a list in TODO List
     * @param id
     * @param status
     * @param authentication
     * @return
     */
    @GetMapping("/lists/{id}/{status}")
    public ResponseEntity<Iterable<TodoItem>> getCompletedTask(@PathVariable("id") Long id, @PathVariable("status") Boolean status,
                                                               Authentication authentication) {
        try {
            HashMap<String, Object> response = todolistService.getCompletedTask(id, status,authentication);
            if(response.get("data") != null){
                Iterable<TodoItem> todoItems = (Iterable<TodoItem>) response.get("data");
                return new ResponseEntity<Iterable<TodoItem>>(todoItems, HttpStatus.FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method will delete a list in TODO List
     * @param id
     * @param authentication
     * @return
     */
    @DeleteMapping("/lists/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                         Authentication authentication) {
        try {
            HashMap<String, Object> response = todolistService.delete(id, authentication);
            if(response.get("data") != null){
                Long deletedItemId = (Long) response.get("data");
                return new ResponseEntity<>(deletedItemId, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method will delete a Task of a list in TODO List
     * @param id
     * @param itemId
     * @param authentication
     * @return
     */
    @DeleteMapping("/lists/{id}/items/{itemId}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                         @PathVariable("itemId") Long itemId,
                                         Authentication authentication) {
        try {
            HashMap<String, Object> response = todolistService.delete(id,itemId,authentication);
            if(response.get("data") != null){
                Long deletedItemId = (Long) response.get("data");
                return new ResponseEntity<>(deletedItemId, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method will update a list in TODO List
     * @param id
     * @param request
     * @param authentication
     * @return
     */
    @PutMapping("/lists/{id}")
    public ResponseEntity<TodoList> update(@PathVariable("id") Long id,
                                         @RequestBody TodoListRequest request,
                                         Authentication authentication) {
        try {
            HashMap<String, Object> response = todolistService.update(id, request, authentication);
            if(response.get("data") != null){
                TodoList todoList = (TodoList) response.get("data");
                return new ResponseEntity<>(todoList, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method will update a Task of a list in TODO List
     * @param id
     * @param itemId
     * @param request
     * @param authentication
     * @return
     */
    @PutMapping("/lists/{id}/items/{itemId}")
    public ResponseEntity<TodoItem> updateItem(@PathVariable("id") Long id,
                                             @PathVariable("itemId") Long itemId,
                                             @RequestBody TodoItemRequest request,
                                             Authentication authentication) {
        try {
            HashMap<String, Object> response = todolistService.updateItem(id, itemId, request, authentication);
            if(response.get("data") != null){
                TodoItem todoItem = (TodoItem) response.get("data");
                return new ResponseEntity<>(todoItem, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
