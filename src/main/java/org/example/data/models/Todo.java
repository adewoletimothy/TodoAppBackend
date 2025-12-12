package org.example.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Component
public class Todo {

    @Id
    private String id;

    private String taskName;
    private Category category;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
//    private LocalDateTime dueDate;
//    private LocalDateTime createdDate;
    private boolean isCompleted;

    private String userId;


}
