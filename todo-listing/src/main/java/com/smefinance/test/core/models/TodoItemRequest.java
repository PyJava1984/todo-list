package com.smefinance.test.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TodoItemRequest {
    private boolean completed;
    private String name;
    private String priority;

    public TodoItemRequest(String name) {
        this.name = name;
    }
}
