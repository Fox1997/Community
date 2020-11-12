package com.example.comm.dto;

import com.example.comm.model.User;
import lombok.Data;

@Data
public class CommentDto {
   private Long id;
   private Long parentId;
   private Integer type;
   private Long commentator;
   private Long gmtCreate;
   private Long gmtModified;
   private Long linkCount;
   private String content;
   private User user;

}
