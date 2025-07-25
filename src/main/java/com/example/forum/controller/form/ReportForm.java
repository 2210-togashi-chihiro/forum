package com.example.forum.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReportForm {

    private int id;

    @NotEmpty(message = "投稿内容を入力してください")
    private String content;

    private Date createdDate;
    private Date updatedDate;
}
