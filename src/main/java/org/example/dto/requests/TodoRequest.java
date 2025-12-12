package org.example.dto.requests;

import lombok.Getter;
import lombok.Setter;
import org.example.data.models.Category;

@Getter
@Setter
public class TodoRequest {
    private String taskName;
    private Category category;
    private boolean isCompleted;
    private String userId;
}
