package com.test.community.controller;

import com.test.community.Dto.CommentDto;
import com.test.community.Dto.QuestionDto;
import com.test.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 查看问题的详细。
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;



    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model) {

        QuestionDto questionDTO = questionService.getQuestionDto(id);
        //浏览数+1
        questionService.viewCount(id);
        List<CommentDto> comments = questionService.getContent(id);


        model.addAttribute("questionDto", questionDTO);
        model.addAttribute("comments", comments);

        return "question";
    }
}
