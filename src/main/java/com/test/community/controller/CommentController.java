package com.test.community.controller;

import com.test.community.Dto.CommentCreateDto;
import com.test.community.Dto.CommentDto;
import com.test.community.Dto.ResultDto;
import com.test.community.mapper.CommentMapper;
import com.test.community.mapper.QuestionMapper;
import com.test.community.mapper.UserMapper;
import com.test.community.model.Comment;
import com.test.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {

    @Resource
    private UserMapper userMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private QuestionMapper questionMapper;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request) {
        //获取user
        User user = (User) request.getSession().getAttribute("user");
        //将前端传递过来的信息上传数据库
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getAccountId());
        comment.setGmtModified(comment.getGmtCreate());
        commentMapper.insert(comment);

        if (commentCreateDto.getType() == 2) {
            commentMapper.updateCommentCount(commentCreateDto.getParentId());
        } else {
            questionMapper.updateCommentCount(commentCreateDto.getParentId());
        }
        ResultDto resultDto = new ResultDto();
        //返回结果
        return resultDto.success();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> comments(@PathVariable(name = "id") int id,
                                                HttpServletRequest request) {
        //查找type=2，即是回复评论的评论
        List<Comment> comments = commentMapper.getCommentById(id, 2);
        List<CommentDto> commentDto = new ArrayList<>();
        //找到User


        //把二级评论和对应的User写进每个CommentDto集合中
        for (Comment comment : comments) {


            User user = userMapper.findByCreator(comment.getCommentator());
            CommentDto dto = new CommentDto();
            BeanUtils.copyProperties(comment, dto);
            dto.setUser(user);
            commentDto.add(dto);
        }
        ResultDto resultDto = new ResultDto();
        return resultDto.success(commentDto);
    }

}
