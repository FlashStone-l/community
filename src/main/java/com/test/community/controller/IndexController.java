package com.test.community.controller;

import com.test.community.Dto.QuestionDto;
import com.test.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    private QuestionService questionService;

    //首页
    @GetMapping("/")
    public String index(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            Model model) {
        //用户持久化登录


        //输入页数小于1时 跳回首页。
        if (page < 1) {
            return "redirect:/";
        }
        List<QuestionDto> questionDtoList = questionService.list(page);
        //注意没有数据的时候。
        int lastPage = questionService.getLastPage();
        if (lastPage == 0) {
            lastPage = 1;
        }
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("currentPage", page);
        //输入页数小于1时 跳回首页。
        if (page > lastPage) {
            return "redirect:/";
        }


        model.addAttribute("questionDtoList", questionDtoList);

        return "index";
    }
}
