package com.example.forum.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentForm {

    private int id;
    private int reportId;

    @NotEmpty(message = "コメントを入力してください")
    private String reContent;

    private Date createdDate;
    private Date updatedDate;
}
