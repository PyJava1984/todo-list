package com.smefinance.test.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="todo_list")
public class TodoList implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int priority;

    @OneToMany(mappedBy = "list",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<TodoItem> items = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "OWNER_USER_ID")
    @JsonIgnore
    private User owner;

    public TodoList(String name, User owner, int priority) {
        this.name = name;
        this.owner = owner;
        this.priority = priority;
    }

    public static TodoList from(TodoListRequest todoListRequest, User user) {
        int priority = 0;
        switch (todoListRequest.getPriority()) {
            case "High": priority = 1; break;
            case "Medium": priority = 2; break;
            case "Low": priority = 3; break;
        }
        return new TodoList(todoListRequest.getName(), user, priority);
    }

    public void merge(TodoListRequest request) {
        setName(request.getName());
        int priority = 0;
        switch (request.getPriority()) {
            case "High": priority = 1; break;
            case "Medium": priority = 2; break;
            case "Low": priority = 3; break;
        }
        setPriority(priority);
    }
}
