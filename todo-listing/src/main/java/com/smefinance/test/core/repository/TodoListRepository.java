package com.smefinance.test.core.repository;

import com.smefinance.test.core.models.TodoList;
import com.smefinance.test.core.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TodoListRepository  extends JpaRepository<TodoList, Long> {
    List<TodoList> findAllByOwner(User owner);
    List<TodoList> findAllByOwnerOrderByPriority(User owner);

    TodoList findOneByIdAndOwner(Long id, User owner);

    @Transactional
    void deleteByIdAndOwner(Long id, User owner);
}
