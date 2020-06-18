package com.test.community.controller;

import com.test.community.mapper.QuestionMapper;
import com.test.community.model.Question;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class greatController {
    @Resource
    private QuestionMapper questionMapper;
    @GetMapping("/great/{qId}")
    public String great(@PathVariable(name = "qId")Integer qId
                       ){
        Question question = questionMapper.getLikeCount(qId);
        Integer likeCount = question.getLikeCount();
        questionMapper.updateLikeCount(qId, likeCount+1);
        return "redirect:/question/"+qId;
    }
}
