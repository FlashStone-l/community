package com.test.community.mapper;

import com.test.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{id},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where account_id=#{id}")
    User findUser(@Param("id") Long id);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id=#{id}")
    void updateUser(User user);
}
