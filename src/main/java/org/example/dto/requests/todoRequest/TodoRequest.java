package org.example.dto.requests.todoRequest;

import lombok.Getter;
import lombok.Setter;
import org.example.data.models.Category;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoRequest {
//    public String getTaskId;
//    private String userId;
    private String taskName;
    private Category category;
    private LocalDateTime dueDate;
}
