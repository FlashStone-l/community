package com.test.community.Dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GiteeUser {
    private Long id;
    private String avatarUrl;
    private String bio;
    private String name;
}
