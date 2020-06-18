package com.test.community.controller;

import com.test.community.Dto.AccessTokenDto;
import com.test.community.Dto.GiteeUser;
import com.test.community.model.User;
import com.test.community.provider.GiteeProvider;
import com.test.community.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    //注入配置里面的值
    @Value("${client_id}")
    private String client_id;
    @Value("${client_secret}")
    private String client_secret;
    @Value("${redirect_url}")
    private String redirect_url;
    //注入GiteeProvider
    @Resource
    private GiteeProvider giteeProvider;

    @Resource
    private UserService userService;

    //获取code并登录,保持登录态。
    @GetMapping("/callback")
    public String Login(@RequestParam(name = "code") String code, HttpServletResponse response
    ) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setRedirect_url(redirect_url);
        String accessToken = giteeProvider.getAccessToken(accessTokenDto);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
        if (giteeUser != null) {
            User user = new User();
            user.setAccountId(giteeUser.getId());
            user.setAvatarUrl(giteeUser.getAvatarUrl());
            user.setName(giteeUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userService.createOrUpdate(user);
            //存入浏览器
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);

            //持久化登录


            return "redirect:/";
        }
        return "redirect:/";


    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
