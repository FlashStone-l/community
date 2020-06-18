package com.test.community.mapper;

import com.test.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("select count(*) from comment where parent_id=#{questionId}")
    Integer updateCommentCount(@Param("questionId") Long questionId);

    @Select("select * from comment where parent_id=#{id}")
    List<Comment> getReplies(Integer id);
    //评论
    @Insert("insert into comment(parent_id,type,commentator,gmt_create,content,gmt_modified) values(#{parentId},#{type},#{commentator},#{gmtCreate},#{content},#{gmtModified})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id=#{id} and type=#{type} order by gmt_create desc")
    List<Comment> getCommentById(@Param("id") int id, @Param("type") int type);
    @Select("select * from comment where id=#{id}")
    Comment findCommentator(int id);


}
