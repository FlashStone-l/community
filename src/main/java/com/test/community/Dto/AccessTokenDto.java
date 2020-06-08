package com.test.community.Dto;

import lombok.Data;

@Data
public class AccessTokenDto {
    private String code;
    private String client_id;
    private String redirect_url;
    private String client_secret;
}
