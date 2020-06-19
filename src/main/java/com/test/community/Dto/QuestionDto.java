package com.test.community.Dto;

import com.test.community.model.User;
import lombok.Data;

/**
 * 关联user表和question表 同时返回user信息和question信息。
 */
@Data
public class QuestionDto {
    private Integer id;
    private Long accountId;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String title;
    private String description;
    private String tag;
    private User user;
    private CommentDto commentDto;
}
