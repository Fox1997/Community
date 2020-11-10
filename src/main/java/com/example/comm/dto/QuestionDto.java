package com.example.comm.dto;

import com.example.comm.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer reviewCount;
    private Integer commentCount;
    private Integer linkCount;
    private User user;


}
