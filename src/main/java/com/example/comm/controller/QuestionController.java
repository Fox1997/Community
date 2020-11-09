package com.example.comm.controller;

import com.example.comm.dto.QuestionDto;
import com.example.comm.mapper.QuestionMapper;
import com.example.comm.model.Question;
import com.example.comm.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id,
                           Model model){
        QuestionDto questionDto=questionService.getById(id);
        model.addAttribute("question",questionDto);
        return "question";
    }
}
