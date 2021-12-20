package com.smefinance.test.core.repository;

import com.smefinance.test.core.models.TodoItem;
import com.smefinance.test.core.models.TodoList;
import com.smefinance.test.core.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    TodoItem findOneByIdAndListAndOwner(Long id, TodoList todoList, User owner);
    List<TodoItem> findByListAndOwnerAndCompleted(TodoList todoList, User owner, Boolean completed);

    @Transactional
    void deleteByIdAndListAndOwner(Long id, TodoList todoList, User owner);
}
