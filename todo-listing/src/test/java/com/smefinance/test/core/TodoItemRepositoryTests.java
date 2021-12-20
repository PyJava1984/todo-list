package com.smefinance.test.core;

import com.smefinance.test.core.models.TodoItem;
import com.smefinance.test.core.models.TodoList;
import com.smefinance.test.core.models.User;
import com.smefinance.test.core.repository.TodoItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoItemRepositoryTests {

    @Autowired
    TodoItemRepository repository;

    @Autowired
    private TestEntityManager entityManager;
    private User owner;
    private TodoList todoList;

    @Before
    public void setUp() throws Exception {
        owner = entityManager.find(User.class, 1L);
        todoList = entityManager.find(TodoList.class, 1L);
    }

    @Test
    public void testCreate() {
        TodoItem todoItem = new TodoItem();
        todoItem.setName("test 1");
        todoItem.setList(todoList);
        todoItem.setOwner(owner);
        repository.save(todoItem);

        TodoItem loadedItem = repository.findOneByIdAndListAndOwner(1L, todoList,owner);
        assertThat(loadedItem.getName()).isEqualTo("test 1");

        TodoList loadedTodoList = entityManager.find(TodoList.class, 1L);
        assertThat(loadedTodoList.getItems()).contains(loadedItem);
    }

    @Test
    public void testGet() {
        TodoItem todoItem = repository.findOneByIdAndListAndOwner(1L, todoList, owner);
        assertThat(todoItem.getName()).isEqualTo("checkitem5");
    }

    @Test
    public void testDelete() {
        repository.deleteByIdAndListAndOwner(1L, todoList, owner);
        assertNull(repository.findOneByIdAndListAndOwner(1L, todoList,owner));
    }
}
