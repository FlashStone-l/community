package com.test.community.service;

import com.test.community.mapper.UserMapper;
import com.test.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser=userMapper.findUser(user.getId());
        if(dbUser==null){
            //如果没有用户，则创建用户
            userMapper.insert(user);
        }else{
            //如果已有用户，更新用户
            user.setGmtModified(user.getGmtCreate());
            userMapper.updateUser(user);
        }

    }
}
