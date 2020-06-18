package com.test.community.mapper;

import com.test.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(creator,title,description,tag,gmt_create,gmt_modified) values(#{creator},#{title},#{description},#{tag},#{gmtCreate},#{gmtModified})")
    void create(Question question);
    @Select("select * from question order by gmt_create desc limit #{index},#{LIMIT}")
    List<Question> list(@Param("index") Integer index,@Param("LIMIT") Integer LIMIT);
    @Select("select count(*) from question")
    int getLastPage();
    //找到question
    @Select("select * from question where id=#{id}")
    Question findQuestion(@Param("id") Integer id);
    //每次请求都更新question中view_count
    @Update("update question set view_count=#{view_count} where id=#{id}")
    void updateViewCount(@Param("view_count") Integer view_count, @Param("id") Integer id);

    @Update("update question set comment_count=comment_count+1 where id=#{parentId}")
    void updateCommentCount(Long parentId);


    @Select("select * from question where creator=#{id} limit #{index},#{LIMIT}")
    List<Question> findByCreator(Long id, Integer index, Integer LIMIT);
    @Select("select count(*) from question where creator=#{id}")
    int pfoLastPage(Long id);
    @Update("update question set like_count=#{likeCount} where id=#{qId}")
    void updateLikeCount(Integer qId, Integer likeCount);
    @Select("select * from question where id=#{qId}")
    Question getLikeCount(Integer qId);
}
