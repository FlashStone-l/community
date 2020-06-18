package com.test.community.service;

import com.test.community.Dto.CommentDto;
import com.test.community.Dto.QuestionDto;
import com.test.community.mapper.CommentMapper;
import com.test.community.mapper.QuestionMapper;
import com.test.community.mapper.UserMapper;
import com.test.community.model.Comment;
import com.test.community.model.Question;
import com.test.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    private final Integer LIMIT=11;
    private Integer index;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CommentMapper commentMapper;
    public List<QuestionDto> list(Integer page){
        index=(page-1)*LIMIT;
        List<Question> questions = questionMapper.list(index,LIMIT);
        List<QuestionDto> questionDtoList=new ArrayList<>();
        for(Question question:questions){

            //获取点赞数
            //未处理

            User user = userMapper.findByCreator(question.getCreator());
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
    public int getLastPage(){
        int page = questionMapper.getLastPage();
        if(page%LIMIT==0){
            return page/LIMIT;
        }else{
            return page/LIMIT+1;
        }
    }

    public void viewCount(Integer id) {
        //通过id查询到question表中的view_count
        Question question = questionMapper.findQuestion(id);//特定的问题
        questionMapper.updateViewCount(question.getViewCount()+1,id);
    }
    //返回问题页面的作者信息
    public QuestionDto getQuestionDto(Integer id) {
        Question question = questionMapper.findQuestion(id);
        User user = userMapper.findByCreator(question.getCreator());
        QuestionDto questionDto=new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setUser(user);
        return  questionDto;
    }

    public List<CommentDto> getContent(Integer id) {
        List<CommentDto> commentDtoList=new ArrayList<>();
        List<Comment> replies = commentMapper.getReplies(id);
        for(Comment comment:replies){
            User user=userMapper.findByCreator(comment.getCommentator());
            CommentDto commentDto=new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(user);
            commentDtoList.add(commentDto);
        }
        return  commentDtoList;
    }


    public List<QuestionDto> findAllByUserId(Long id, Integer page) {
        index=(page-1)*LIMIT;
        List<QuestionDto> questionDtoList=new ArrayList<>();
        List<Question> questions=questionMapper.findByCreator(id,index,LIMIT);
        for(Question question:questions){
            User user=userMapper.findByCreator(question.getCreator());
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return  questionDtoList;
    }

    public Integer pfoLastPage(Long id) {
        int page = questionMapper.pfoLastPage(id);
        if(page%LIMIT==0){
            return page/LIMIT;
        }else{
            return page/LIMIT+1;
        }
    }
}
