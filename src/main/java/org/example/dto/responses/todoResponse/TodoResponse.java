package org.example.dto.responses.todoResponse;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class TodoResponse {
    private Long id;
    private String taskName;
    private String category;
    private LocalDateTime dueDate;
    private boolean isCompleted;
    private LocalDateTime createdDate;
    private LocalDateTime completedDate;

    private Long userId;
}
