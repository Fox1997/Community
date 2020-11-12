package com.example.comm.controller;

import com.example.comm.dto.CommentDto;
import com.example.comm.dto.ResultDTo;
import com.example.comm.exception.CustomizeErrorCode;
import com.example.comm.model.Comment;
import com.example.comm.model.User;
import com.example.comm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired(required = false)
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDto commentDto,
                          HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null)
        {
            return ResultDTo.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLinkCount(0L);
        commentService.insert(comment);
        return ResultDTo.okOf();


    }

}
