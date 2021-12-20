package com.smefinance.test.core;

import com.smefinance.test.core.SpringBootSecurityJwtApplicationTests;
import com.smefinance.test.core.models.TodoList;
import com.smefinance.test.core.models.User;
import com.smefinance.test.core.repository.TodoListRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoListRepositoryTests {

    @Autowired
    private TodoListRepository repository;

    @Autowired
    private TestEntityManager entityManager;
    private User owner;

    @Before
    public void setUp() throws Exception {
        owner = entityManager.find(User.class, 1L);
    }

    @Test
    public void testCreate() {
        TodoList chores = new TodoList("chores", owner, 1);
        TodoList todoList = repository.save(chores);
        assertThat(todoList.getName()).isEqualTo("chores");
        assertThat(todoList.getOwner()).isEqualTo(owner);
    }

    @Test
    public void testGet() {
        TodoList todoList = repository.findOneByIdAndOwner(23L, owner);
        assertThat(todoList.getName()).isEqualTo("Test List 23");
    }

    @Test
    public void testList() {
        List<TodoList> lists = repository.findAllByOwner(owner);
        assertThat(lists.size()).isEqualTo(1);
    }

    @Test
    public void testDelete() {
        repository.deleteByIdAndOwner(23L, owner);
        assertNull(repository.findOneByIdAndOwner(23L, owner));
    }
}
