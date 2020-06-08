package com.test.community.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String bio;
    private String name;
    private String avatarUrl;
    private Long gmtCreate;
    private Long gmtModified;
    private String token;
}
