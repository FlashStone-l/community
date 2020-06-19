package com.test.community.controller;

import com.test.community.Dto.GiteeUser;
import com.test.community.mapper.QuestionMapper;
import com.test.community.model.Question;
import com.test.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PublishController {
    //注入QuestionMapper
   @Resource
    private QuestionMapper questionMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    //查看问题


    @PostMapping("/publish")
    public String index(
            @RequestParam(name = "description") String description,
            @RequestParam(name="tag") String tag,
            @RequestParam(name = "title") String title,
            HttpServletRequest request){
        Question question=new Question();
        question.setDescription(description);
        question.setTag(tag);
        question.setTitle(title);
        HttpSession session = request.getSession();
        User user =(User) session.getAttribute("user");
        question.setAccountId(user.getAccountId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(user.getGmtModified());
        System.out.println(user.getAccountId());
        questionMapper.create(question);
        return "redirect:/";
    }
}
