package com.smefinance.test.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smefinance.test.core.enumeration.TPriority;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString(exclude = {"list"})
@Entity
@Table(name="todo_item")
public class TodoItem implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TODO_LIST_ID")
    @JsonIgnore
    private TodoList list;

    @OneToOne
    @JoinColumn(name = "OWNER_USER_ID")
    @JsonIgnore
    private User owner;

    private int priority;

    public TodoItem(String name, TodoList list, User owner, int priority) {
        this.name = name;
        this.list = list;
        this.owner = owner;
        this.priority = priority;
    }

    public static TodoItem from(TodoItemRequest todoItemRequest, TodoList todoList) {
        int priority = 0;
        switch (todoItemRequest.getPriority()) {
            case "High": priority = 1; break;
            case "Medium": priority = 2; break;
            case "Low": priority = 3; break;
        }
        return new TodoItem(todoItemRequest.getName(), todoList, todoList.getOwner(), priority);
    }

    public void merge(TodoItemRequest request) {
        this.name = request.getName();
        this.completed = request.isCompleted();
        int priority = 0;
        switch (request.getPriority()) {
            case "High": priority = 1; break;
            case "Medium": priority = 2; break;
            case "Low": priority = 3; break;
        }
        this.priority= priority;
    }
}
