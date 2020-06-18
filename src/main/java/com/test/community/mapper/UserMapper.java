package com.test.community.mapper;

import com.test.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);




    @Update("update user set name=#{name},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl},token=#{token} where account_id=#{accountId}")
    void updateUser(User user);
    //发现问题列表 拿到用户头像
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Long id);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param(value = "token") String token);


    @Select("select * from user where account_id=#{id}")
    User findByCreator(Long creator);
}
