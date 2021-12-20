package com.smefinance.test.core.service;

import com.smefinance.test.core.exception.BaseException;
import com.smefinance.test.core.exception.InvalidInputException;
import com.smefinance.test.core.models.*;
import com.smefinance.test.core.repository.TodoItemRepository;
import com.smefinance.test.core.repository.TodoListRepository;
import com.smefinance.test.core.security.services.UserDetailsImpl;
import com.smefinance.test.core.serviceImpl.ITodoListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class TodolistService implements ITodoListService {

    //region variable and DI
    private static final Logger logger = LoggerFactory.getLogger(TodolistService.class);
    @Autowired
    private TodoListRepository listRepository;

    @Autowired
    private TodoItemRepository itemRepository;
    //endregion


    //region public

    /**
     *
     * @param todoListRequest
     * @param authentication
     * @return
     */
    public HashMap<String, Object> create(TodoListRequest todoListRequest, Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            TodoList todoList = listRepository.save(TodoList.from(todoListRequest, getOwnerFromAuthentication(authentication)));
            response.put("data", todoList);
            response.put("status", HttpStatus.CREATED);
        }catch(Exception e){
            response.put("data", null);
            response.put("status", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     *
     * @param id
     * @param todoItemRequest
     * @param authentication
     * @return
     */
    public HashMap<String, Object> createItem(Long id, TodoItemRequest todoItemRequest,
                                              Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            TodoList todoList = listRepository.findOneByIdAndOwner(id, getOwnerFromAuthentication(authentication));
            TodoItem todoItem = itemRepository.save(TodoItem.from(todoItemRequest, todoList));
            response.put("data", todoItem);
            response.put("status", HttpStatus.CREATED);
        }catch(Exception e){
            response.put("data", null);
            response.put("status", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     *
     * @param authentication
     * @return
     */
    public HashMap<String, Object> list(Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            List<TodoList> allByOwner = listRepository.findAllByOwnerOrderByPriority(getOwnerFromAuthentication(authentication));
            response.put("data", allByOwner);
            response.put("status", HttpStatus.FOUND);
        }catch(Exception e){
            response.put("data", null);
            response.put("status", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     *
     * @param id
     * @param authentication
     * @return
     */
    public HashMap<String, Object> get(Long id,
                                        Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            TodoList todoList = listRepository.findOneByIdAndOwner(id, getOwnerFromAuthentication(authentication));
            response.put("data", todoList);
            response.put("status", HttpStatus.FOUND);
        }catch(Exception e){
            response.put("data", null);
            response.put("status", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     *
     * @param id
     * @param status
     * @param authentication
     * @return
     */
    public HashMap<String, Object> getCompletedTask(Long id, Boolean status,
                                                     Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            TodoList todoList = listRepository.findOneByIdAndOwner(id, getOwnerFromAuthentication(authentication));
            List<TodoItem> todoItems = itemRepository.findByListAndOwnerAndCompleted(todoList, getOwnerFromAuthentication(authentication), status);
            response.put("data", todoItems);
            response.put("status", HttpStatus.FOUND);
        }catch(Exception e){
            response.put("data", null);
            response.put("status", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     *
     * @param id
     * @param authentication
     * @return
     */
    public HashMap<String, Object> delete(Long id,
                                         Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            listRepository.deleteByIdAndOwner(id, getOwnerFromAuthentication(authentication));
            response.put("data", id);
            response.put("status", HttpStatus.NO_CONTENT);
        }catch(Exception e){
            response.put("data", null);
            response.put("status", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     *
     * @param id
     * @param itemId
     * @param authentication
     * @return
     */
    public HashMap<String, Object> delete(Long id,
                                         Long itemId,
                                         Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            TodoList todoList = listRepository.findOneByIdAndOwner(id, getOwnerFromAuthentication(authentication));
            itemRepository.deleteByIdAndListAndOwner(itemId, todoList, getOwnerFromAuthentication(authentication));
            response.put("data", id);
            response.put("status", HttpStatus.NO_CONTENT);
        }catch(Exception e){
            response.put("data", null);
            response.put("status", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     *
     * @param id
     * @param request
     * @param authentication
     * @return
     */
    @Transactional
    public HashMap<String, Object> update(Long id,
                                         TodoListRequest request,
                                         Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            TodoList todoList = listRepository.findOneByIdAndOwner(id, getOwnerFromAuthentication(authentication));
            todoList.merge(request);
            listRepository.save(todoList);
            response.put("data", todoList);
            response.put("status", HttpStatus.ACCEPTED);
        }catch(Exception e){
            response.put("data", null);
            response.put("status", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     *
     * @param id
     * @param itemId
     * @param request
     * @param authentication
     * @return
     */
    @Transactional
    public HashMap<String, Object> updateItem(Long id,
                                             Long itemId,
                                             TodoItemRequest request,
                                             Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            TodoList todoList = listRepository.findOneByIdAndOwner(id, getOwnerFromAuthentication(authentication));
            TodoItem todoItem = itemRepository.findOneByIdAndListAndOwner(itemId, todoList, getOwnerFromAuthentication(authentication));
            todoItem.merge(request);
            itemRepository.save(todoItem);
            response.put("data", todoItem);
            response.put("status", HttpStatus.ACCEPTED);
        }catch(Exception e){
            response.put("data", null);
            response.put("status", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    //endregion

    //region private

    /**
     * This method will get user information
     * @param authentication
     * @return
     */
    private User getOwnerFromAuthentication(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = new User();
        user.setId(userDetails.getId());
        return user;
    }
    //endregion
}
