package com.smefinance.test.core.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoListRequest {
    private String name;
    private String priority;
    public TodoListRequest(String name) {
        this.name = name;
    }
}
