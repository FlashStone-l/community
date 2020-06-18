package com.test.community.controller;

import com.test.community.Dto.QuestionDto;
import com.test.community.model.User;
import com.test.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class ProfileController {

    @Resource
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page){

        if (page < 1) {
            return "redirect:/";
        }
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        if ("questions".contains(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的问题");
        }else if ("replies".contains(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        List<QuestionDto> myQuestions = questionService.findAllByUserId(user.getAccountId(), page);
        Integer lastPage=questionService.pfoLastPage(user.getAccountId());
        if (lastPage == 0) {
            lastPage = 1;
        }
        if (page > lastPage) {
            return "redirect:/";
        }
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("currentPage",page);
        model.addAttribute("questionDto",myQuestions);
        return "profile";

    }
}
