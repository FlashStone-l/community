package com.test.community.Dto;

import lombok.Data;

/**
 * 用于封装前端传来的数据
 */
@Data
public class CommentCreateDto {
    private Long parentId;
    private String content;
    private Integer type;
}
