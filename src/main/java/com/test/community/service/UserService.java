package com.test.community.service;

import com.test.community.mapper.UserMapper;
import com.test.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser=userMapper.findByCreator(user.getAccountId());
        if(dbUser==null){
            //如果没有用户，则创建用户
            user.setGmtCreate(System.currentTimeMillis());
            userMapper.insert(user);
        }else{
            //如果已有用户，更新用户

            user.setGmtModified(System.currentTimeMillis());
            user.setToken(user.getToken());
            userMapper.updateUser(user);
        }

    }
}
