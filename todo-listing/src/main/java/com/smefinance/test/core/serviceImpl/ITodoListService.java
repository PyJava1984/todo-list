package com.smefinance.test.core.serviceImpl;

import com.smefinance.test.core.models.TodoItemRequest;
import com.smefinance.test.core.models.TodoListRequest;
import org.springframework.security.core.Authentication;

import java.util.HashMap;

public interface ITodoListService {

    public HashMap<String, Object> create(TodoListRequest todoListRequest, Authentication authentication);
    public HashMap<String, Object> createItem(Long id, TodoItemRequest todoItemRequest,
                                              Authentication authentication);
    public HashMap<String, Object> list(Authentication authentication);
    public HashMap<String, Object> get(Long id,
                                       Authentication authentication);
    public HashMap<String, Object> getCompletedTask(Long id, Boolean status,
                                                    Authentication authentication);
    public HashMap<String, Object> delete(Long id,
                                          Authentication authentication);
    public HashMap<String, Object> delete(Long id,
                                          Long itemId,
                                          Authentication authentication);
    public HashMap<String, Object> update(Long id,
                                          TodoListRequest request,
                                          Authentication authentication);
    public HashMap<String, Object> updateItem(Long id,
                                              Long itemId,
                                              TodoItemRequest request,
                                              Authentication authentication);
}
