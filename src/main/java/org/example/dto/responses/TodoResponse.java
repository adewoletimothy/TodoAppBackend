package org.example.dto.responses;

import lombok.Data;
import org.example.data.models.Category;

@Data
public class TodoResponse {
    private String id;

    private String taskName;
    private Category category;
    private boolean isCompleted;

    private String userId;
}
