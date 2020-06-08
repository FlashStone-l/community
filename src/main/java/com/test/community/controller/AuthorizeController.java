package com.test.community.controller;

import com.test.community.Dto.AccessTokenDto;
import com.test.community.Dto.GiteeUser;
import com.test.community.model.User;
import com.test.community.provider.GiteeProvider;
import com.test.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private GiteeProvider giteeProvider;

    @Autowired
    private UserService userService;
    //获取code并登录,保持登录态。
    @GetMapping("/callback")
    public String Login(@RequestParam(name = "code") String code,
                        HttpServletRequest request
                       ){
        AccessTokenDto accessTokenDto=new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setRedirect_url(redirect_url);
        String accessToken = giteeProvider.getAccessToken(accessTokenDto);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
        if(giteeUser!=null){
            request.getSession().setAttribute("user",giteeUser);
            User user=new User();
            user.setId(giteeUser.getId());
            user.setAvatarUrl(giteeUser.getAvatarUrl());
            user.setName(giteeUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setToken(UUID.randomUUID().toString());
            userService.createOrUpdate(user);
           /* 这种每次关闭服务器 会重复创建用户userMapper.insert(user);*/
            return "redirect:/";
        }
        return "redirect:/";
    }
}
