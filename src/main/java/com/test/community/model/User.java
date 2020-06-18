package com.test.community.model;

import lombok.Data;

@Data
public class User {
    private Long accountId;
    private String name;
    private String avatarUrl;
    private Long gmtCreate;
    private Long gmtModified;
    private String token;
}
